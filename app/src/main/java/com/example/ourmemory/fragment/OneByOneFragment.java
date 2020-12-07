package com.example.ourmemory.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ourmemory.ContactUsActivity;
import com.example.ourmemory.R;
import com.example.ourmemory.SessionManager;
import com.example.ourmemory.photoHelper.FileUtils;
import com.example.ourmemory.photoHelper.PhotoHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

import static android.app.Activity.RESULT_OK;

public class OneByOneFragment extends Fragment implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {
    Button buttonOnepicture, buttonOneOK;
    EditText editTextOne1, editTextOne2,editTextOne3, editTextOne4;
    RadioGroup radioGroup;
    ImageView imageViewOne;
    LinearLayout oneLayout;
    View rootView;
    Context context;
    ContactUsActivity activity;
    String filePath, realFileName;
    SessionManager sessionManager;
    HttpResponse response;
    AsyncHttpClient client;
    public OneByOneFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_one_by_one, container, false);
        oneLayout = rootView.findViewById(R.id.oneLayout);
        activity = new ContactUsActivity();
        response = new HttpResponse();
        client = new AsyncHttpClient();

        sessionManager = new SessionManager(context);

        buttonOnepicture = rootView.findViewById(R.id.buttonOnepicture);
        buttonOneOK = rootView.findViewById(R.id.buttonOneOK);

        imageViewOne = rootView.findViewById(R.id.imageViewOne);

        editTextOne1 = rootView.findViewById(R.id.editTextOne1);
        editTextOne2 = rootView.findViewById(R.id.editTextOne2);
        editTextOne3 = rootView.findViewById(R.id.editTextOne3);
        editTextOne4 = rootView.findViewById(R.id.editTextOne4);

        radioGroup = rootView.findViewById(R.id.radioGroup);

        radioGroup.setOnCheckedChangeListener(this);
        buttonOnepicture.setOnClickListener(this);
        buttonOneOK.setOnClickListener(this);
        imageViewOne.setVisibility(View.GONE);
        return rootView;
    }

    @Override
    public void onCheckedChanged(RadioGroup radioGroup, int i) {
        switch (radioGroup.getId()){
            case R.id.radioGroup:
                RadioButton radioButton = rootView.findViewById(i);
                if (radioButton.getText().equals("휴대폰")){
                    editTextOne3.setVisibility(View.VISIBLE);
                    editTextOne4.setVisibility(View.GONE);
                } else {
                    editTextOne3.setVisibility(View.GONE);
                    editTextOne4.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.buttonOneOK:
                postJsonData();
                break;
            case R.id.buttonOnepicture:
                permissionCheck();
                showSelect();
                break;
        }
    }

    private void postJsonData() {
        int checkedId = radioGroup.getCheckedRadioButtonId();
        RadioButton radioButton = rootView.findViewById(checkedId);

        HashMap<String, String> user = sessionManager.getUserDetail();
        String session_id = user.get(sessionManager.ID);
        String session_name = user.get(sessionManager.NAME);

        String subject = editTextOne1.getText().toString();
        String content = editTextOne2.getText().toString();
        String phone= editTextOne3.getText().toString();
        String email = editTextOne4.getText().toString();
        String callBack = "";

        if (subject.equals("")||content.equals("")){
            Toast.makeText(context,"내용을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (phone.equals("")&&email.equals("")){
            Toast.makeText(context,"답변 받을 방식을 선택 후 입력해주세요.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (radioButton.getText().equals("휴대폰")){
            email = "X";
            callBack = "please reply to phone";
        } else {
            phone = "X";
            callBack = "please reply to email";
        }
        RequestParams params = new RequestParams();

        String url = "http://192.168.1.21:8085/java/contactUsAndroid";
        params.put("contact_name", session_id);
        params.put("contact_id", session_name);
        params.put("contact_email",email);
        params.put("contact_tel",phone);
        params.put("contact_callback",callBack);
        params.put("contact_subject",subject);
        params.put("contact_content",content);

        if (filePath!=null){
            try {
                params.put("contact_file", new File(filePath));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        // 파일을 보내려면 사용하는 코드
        params.setForceMultipartEntityContentType(true);
        client.post(url,params,response);
    }

    private void permissionCheck() {
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 101);
            }
        }
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.CAMERA)) {

            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.CAMERA}, 100);
            }
        }
        if (ActivityCompat.checkSelfPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 102 );
            }
        }
    }
    private void showSelect() {
        final String [] menu = {"새로 촬영하기", "갤러리에서 가져오기"};
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
                            uri = FileProvider.getUriForFile(context,
                                    context.getPackageName()
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
                        intent = new Intent(Intent.ACTION_PICK);
                        intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
//                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true); 여러장 선택 가능한 함수
                        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent,101);
                        break;
                }
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Intent intent;
        if(resultCode==RESULT_OK) {
            switch (requestCode) {
                case 100:
                    Toast.makeText(context, "사진 첨부 완료", Toast.LENGTH_SHORT).show();
                    //촬영 결과물을 MediaStore에 등록한다(갤러리에 저장). MediaStore에 등록하지 않으면 우리 앱에서 만든 파일을 다른 앱에서는 사용할 수 없다.
                    intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(filePath));
                    Log.d("[TEST]", filePath);
                    context.sendBroadcast(intent);
                    realFileName = filePath.substring(filePath.lastIndexOf("/")+1);
                    imageViewOne.setVisibility(View.VISIBLE);
                    Glide.with(this).load(filePath)
                            .into(imageViewOne);
                    break;
                case 101 :
                    String uri = data.getData().toString();
                    String fileName = uri.substring(uri.lastIndexOf("/")+1);
                    Log.d("[TEST]", "fileName = "+fileName);
                    filePath = FileUtils.getPath(context, data.getData());
                    Log.d("[TEST]", "filePath = "+filePath);
                    Toast.makeText(context, fileName+"을 선택하셨습니다.", Toast.LENGTH_SHORT).show();
                    realFileName = filePath.substring(filePath.lastIndexOf("/")+1);
                    imageViewOne.setVisibility(View.VISIBLE);
                    Glide.with(this).load(filePath)
                            .into(imageViewOne);
                    break;
            }
        }
    }

    class HttpResponse extends AsyncHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                String rt = json.getString("rt");
                if (rt.equals("OK")){
                    Toast.makeText(context,"문의 내용을 접수 하였습니다.",Toast.LENGTH_SHORT).show();
                    imageViewOne.setVisibility(View.GONE);
                    editTextOne1.setText("");
                    editTextOne2.setText("");
                    editTextOne3.setText("");
                    editTextOne4.setText("");
                } else {
                    Toast.makeText(context,"문의 접수를 실패 하였습니다.",Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(context,"false",Toast.LENGTH_SHORT).show();
            String str = new String(responseBody);
            Log.d("[TEST]",str);
        }
    }
}