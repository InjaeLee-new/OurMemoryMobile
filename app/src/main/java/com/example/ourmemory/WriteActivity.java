package com.example.ourmemory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ourmemory.helper.FileUtils;
import com.example.ourmemory.helper.PhotoHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;

public class WriteActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonWrite, buttonWriteCancel, buttonWriteImage;
    EditText editTextWrite1, editTextWrite2, editTextWrite3, editTextWrite4, editTextWrite5;
    TextView textViewImage;
    ImageView imageView;
    Spinner spinner;
    String filePath = null;
    HttpResponse response;
    AsyncHttpClient client;

    String realFileName; // DB에 저장할 파일 이름
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        buttonWrite = findViewById(R.id.buttonWrite);
        buttonWriteCancel = findViewById(R.id.buttonWriteCancel);
        buttonWriteImage = findViewById(R.id.buttonWriteImage);
        imageView = findViewById(R.id.imageView);
        editTextWrite1 = findViewById(R.id.editTextWrite1);
        editTextWrite2 = findViewById(R.id.editTextWrite2);
        editTextWrite3 = findViewById(R.id.editTextWrite3);
        editTextWrite4 = findViewById(R.id.editTextWrite4);
        editTextWrite5 = findViewById(R.id.editTextWrite5);

        textViewImage = findViewById(R.id.textViewImage);

        // 스피너 객체 호출
        spinner = (Spinner)findViewById(R.id.spinner);
        ArrayAdapter cateAdapter = ArrayAdapter.createFromResource(this,
                R.array.category_item, android.R.layout.simple_spinner_item);
        cateAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(cateAdapter);

        response = new HttpResponse();
        client = new AsyncHttpClient();

        buttonWrite.setOnClickListener(this);
        buttonWriteImage.setOnClickListener(this);
        buttonWriteCancel.setOnClickListener(this);

        permissionCheck();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonWrite:
                postJsonData();
                break;
            case R.id.buttonWriteImage:
                showSelect();
                break;
            case R.id.buttonWriteCancel:
                finish();
                break;
        }
    }

    private void permissionCheck() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)){

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101 );
            }
        }
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.CAMERA)){

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.CAMERA}, 100 );
            }
        }
    }
    private void showSelect() {
        final String [] menu = {"새로 촬영하기", "갤러리에서 가져오기"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(menu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                switch (which) {
                    case 0: //새로 촬영하기-카메라 호출
                        filePath = PhotoHelper.getInstance().getNewPhotoPath();
                        // 카메라 앱 호출
                        File file = new File(filePath);
                        Uri uri = null;
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N ){
                            uri = FileProvider.getUriForFile(getApplicationContext(),
                                    getApplicationContext().getPackageName()
                                            + ".fileprovider", file);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        } else {
                            uri = Uri.fromFile(file);
                        }
                        Log.d("[TEST]","uri = "+uri);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                        startActivityForResult(intent,100);
                        break;
                    case 1: //갤러리에서 가져오기-갤러리 호출
                        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_LOCAL_ONLY,true);
                        startActivityForResult(intent,101);
                        break;
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent;
        if(resultCode==RESULT_OK) {
            switch (requestCode) {
                case 100:
                    Toast.makeText(this, "사진 첨부 완료", Toast.LENGTH_SHORT).show();
                    //촬영 결과물을 MediaStore에 등록한다(갤러리에 저장). MediaStore에 등록하지 않으면 우리 앱에서 만든 파일을 다른 앱에서는 사용할 수 없다.
                    intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(filePath));
                    Log.d("[TEST]", filePath);
                    sendBroadcast(intent);
                    realFileName = filePath.substring(filePath.lastIndexOf("/")+1);
                    textViewImage.setText(realFileName);
                    Glide.with(this).load(filePath)
                            .into(imageView);
                    buttonWriteImage.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    break;
                case 101 :
                    String uri = data.getData().toString();
                    String fileName = uri.substring(uri.lastIndexOf("/")+1);
                    Log.d("[TEST]", "fileName = "+fileName);
                    filePath = FileUtils.getPath(this, data.getData());
                    Log.d("[TEST]", "filePath = "+filePath);
                    Toast.makeText(this, fileName+"을 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                    realFileName = filePath.substring(filePath.lastIndexOf("/")+1);
                    textViewImage.setText(realFileName);
                    Glide.with(this).load(filePath)
                            .into(imageView);
                    buttonWriteImage.setVisibility(View.GONE);
                    imageView.setVisibility(View.VISIBLE);
                    break;
            }
        }
    }
    private void postJsonData() {
        String category = spinner.getSelectedItem().toString();
        String memory_name = editTextWrite1.getText().toString().trim();
        String memory_id = editTextWrite2.getText().toString().trim();
        String memory_pass = editTextWrite3.getText().toString().trim();
        String memory_subject = editTextWrite4.getText().toString().trim();
        String memory_content = editTextWrite5.getText().toString().trim();

        String msg = "";
        if (spinner.getSelectedItemPosition()==0) {
            Toast.makeText(this,"카테고리를 선택하세요.",Toast.LENGTH_SHORT).show();
            return;
        }
        if (msg.equals("")&&memory_name.equals("")){
            msg = "이름을 입력하세요.";
        } else if (msg.equals("")&&memory_pass.equals("")){
            msg = "비밀번호를 입력하세요.";
        } else if (msg.equals("")&&memory_id.equals("")){
            msg = "아이디를 입력하세요.";
        } else if (msg.equals("")&&memory_subject.equals("")){
            msg = "제목을 입력하세요.";
        } else if (msg.equals("")&&memory_content.equals("")){
            msg = "내용을 입력하세요.";
        }
        if (!msg.equals("")){
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
            return;
        }

        RequestParams params = new RequestParams();
        String url = "http://192.168.0.109:8082/java/writeAndroid";
        params.put("memory_name",memory_name);
        params.put("memory_id",memory_id);
        params.put("memory_pass",memory_pass);
        params.put("memory_subject",memory_subject);
        params.put("memory_content",memory_content);
        params.put("memory_category",category);
        try {
            params.put("memory_file", new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // 파일을 보내려면 사용하는 코드
        params.setForceMultipartEntityContentType(true);
        client.post(url,params,response);
    }

    class HttpResponse extends AsyncHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                String rt = json.getString("rt");
                if (rt.equals("OK")){
                    Toast.makeText(WriteActivity.this,"저장 성공",Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(WriteActivity.this,"저장 실패",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(WriteActivity.this,"false",Toast.LENGTH_SHORT).show();
            String str = new String(responseBody);
            Log.d("[TEST]",str);
        }
    }
}
