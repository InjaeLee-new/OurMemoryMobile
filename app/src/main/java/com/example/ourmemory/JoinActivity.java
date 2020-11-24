package com.example.ourmemory;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.ourmemory.helper.JsonJoinHelper;
import com.example.ourmemory.photoHelper.FileUtils;
import com.example.ourmemory.photoHelper.PhotoHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.io.File;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonJoin, buttonBack, buttonForTest;

    String [] category = {"memory", "pet", "health"};

    int checkCategory = 0;

    ImageButton imageButton;

    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8;

    EditText editTextName, editTextId, editTextPwd, editTextNick, editTextTel1, editTextTel2, editTextTel3,
            editTextEmail1, editTextEmail2, editTextAddr;

    RadioGroup radioGender;
    RadioButton radioBtnM, radioBtnF;

    JsonJoinHelper helper;
    AsyncHttpClient client;

    String filepath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        client = new AsyncHttpClient();
        helper = new JsonJoinHelper(this);


        buttonJoin = findViewById(R.id.buttonJoin);
        buttonBack = findViewById(R.id.buttonBack);

        imageButton = findViewById(R.id.imageButton);
        buttonJoin = findViewById(R.id.buttonJoin);
        buttonBack = findViewById(R.id.buttonBack);
        buttonForTest = findViewById(R.id.buttonForTest);

        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        checkBox6 = findViewById(R.id.checkBox6);
        checkBox7 = findViewById(R.id.checkBox7);
        checkBox8 = findViewById(R.id.checkBox8);

        editTextName = findViewById(R.id.editTextName);
        editTextId = findViewById(R.id.editTextId);
        editTextPwd = findViewById(R.id.editTextPwd);
        editTextNick = findViewById(R.id.editTextNick);
        editTextEmail1 = findViewById(R.id.editTextEmail1);
        editTextEmail2 = findViewById(R.id.editTextEmail2);
        editTextAddr = findViewById(R.id.editTextAddr);

        editTextTel1 = findViewById(R.id.editTextTel1);
        editTextTel2 = findViewById(R.id.editTextTel2);
        editTextTel3 = findViewById(R.id.editTextTel3);

        radioGender = findViewById(R.id.radioGender);
        radioBtnM = findViewById(R.id.radioBtnM);
        radioBtnF = findViewById(R.id.radioBtnF);

        buttonJoin.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
        imageButton.setOnClickListener(this);
        buttonForTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonJoin:
                MemoryJoinAction();
                finish();
                break;
            case R.id.buttonBack:
                finish();
                break;
            case R.id.imageButton:
                showPhotoDialog();
                break;
            case R.id.buttonForTest:
                Toast.makeText(this, editTextId.getText().toString(), Toast.LENGTH_SHORT).show();
                break;

        }
    }

    public void MemoryJoinAction() {
        RequestParams params = new RequestParams();
        params.put("profile", filepath);
        params.put("name", editTextName.getText().toString().trim());
        params.put("id", editTextId.getText().toString().trim());
        params.put("pw", editTextPwd.getText().toString().trim());
        params.put("nickname", editTextNick.getText().toString().trim());
//        params.put("gender", radioGender.isSelected());
        params.put("gender", 1);
        params.put("email1", editTextEmail1.getText().toString().trim());
        params.put("email2", editTextEmail2.getText().toString().trim());
        params.put("tel1", editTextTel1.getText().toString().trim());
        params.put("tel2", editTextTel2.getText().toString().trim());
        params.put("tel3", editTextTel3.getText().toString().trim());
        params.put("addr", editTextAddr.getText().toString().trim());
        params.put("cate1","health");
        params.put("cate2","pet");
        params.put("cate3","food");
        params.put("google_Id", "23523523");
        params.put("kakao_Id", "235235");
        String url = "http://192.168.1.21:8085/java/joinJson";
        client.post(url, params, helper);
        Toast.makeText(this,"회원가입 성공", Toast.LENGTH_SHORT).show();
    }

    private void showPhotoDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String[] items = {"새로 촬영하기", "갤러리에서 가져오기"};

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = null;
                switch (which) {
                    case 0:
                        filepath = PhotoHelper.getInstance().getNewPhotoPath();

                        File file = new File(filepath);
                        Uri uri = null;
                        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                            uri = FileProvider.getUriForFile(getApplicationContext(),
                                    getApplicationContext().getPackageName() + ".fileprovider", file);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                            startActivityForResult(intent, 100);
                        } else {
                            uri = Uri.fromFile(file);
                        }
                        break;
                    case 1:
                        intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        intent.setType("image/*");
                        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                        startActivityForResult(intent, 101);
                        break;
                }
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 100:
                Intent photoIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                        Uri.parse("file://" + filepath));
                sendBroadcast(photoIntent);
                break;
            case 101:
                if(resultCode == RESULT_OK) {
                    filepath = FileUtils.getPath(this, data.getData());
                }
                break;
        }
    }
}