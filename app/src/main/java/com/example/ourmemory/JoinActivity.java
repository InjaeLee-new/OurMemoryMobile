package com.example.ourmemory;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ourmemory.helper.JsonJoinHelper;
import com.example.ourmemory.photoHelper.FileUtils;
import com.example.ourmemory.photoHelper.PhotoHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.io.File;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonJoin, buttonBack, buttonForTest;

    ImageButton imageButton;

    CheckBox[] checkBoxes;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8;

    EditText editTextName, editTextId, editTextPwd, editTextNick, editTextTel1, editTextTel2, editTextTel3,
            editTextEmail1, editTextEmail2, editTextAddr;

    String [] categoryName = {"memory", "pet", "it", "game" , "food", "music", "art", "health"};
    String [] category = new String[3];
//    int [] checkBox = {R.id.checkBox1, R.id.checkBox2, R.id.checkBox3, R.id.checkBox4, R.id.checkBox4
//            , R.id.checkBox5, R.id.checkBox6, R.id.checkBox7};
    int checkCategory = 0;


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

        Log.d("[TEST]", "" +checkCategory);
        if(category[checkCategory] != null) {
            Log.d("[TEST]", category[checkCategory]);
        } else {
            Log.d("[TEST]", checkCategory + "인덱스의 category값이 null입니다.");
        }


        buttonJoin = findViewById(R.id.buttonJoin);
        buttonBack = findViewById(R.id.buttonBack);

        imageButton = findViewById(R.id.imageButton);
        buttonJoin = findViewById(R.id.buttonJoin);
        buttonBack = findViewById(R.id.buttonBack);
        buttonForTest = findViewById(R.id.buttonForTest);

//        checkBoxes[0] = findViewById(checkBox[0]);

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

        checkBox1.setOnClickListener(this);
        checkBox2.setOnClickListener(this);
        checkBox3.setOnClickListener(this);
        checkBox4.setOnClickListener(this);
        checkBox5.setOnClickListener(this);
        checkBox6.setOnClickListener(this);
        checkBox7.setOnClickListener(this);
        checkBox8.setOnClickListener(this);

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
                String result = "";
                for(int i = 0 ; i < category.length; i++) {
                    result += i+1 + "번째 카테고리" + category[i] + "\n";
                }
                Toast.makeText(this, result , Toast.LENGTH_SHORT).show();
                break;

            case R.id.checkBox1:
                    if(checkBox1.isChecked()) {
                        if(checkCategory < 3) {
                            category[checkCategory] = categoryName[0];
                            checkCategory += 1;
                        } else {
                            Toast.makeText(this,
                                    "카테고리는 3개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show();
                            checkBox1.setChecked(false);
                        }
                    }
                    else {
                        if(checkCategory > 0) {
                            checkCategory -= 1;
                        }
                        category[checkCategory] = null;
                }
                break;
            case R.id.checkBox2:
                if(checkBox2.isChecked()) {
                    if(checkCategory < 3) {
                        category[checkCategory] = categoryName[1];
                        checkCategory += 1;
                    } else {
                        Toast.makeText(this,
                                "카테고리는 3개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show();
                        checkBox2.setChecked(false);
                    }
                }
                else {
                    if(checkCategory > 0) {
                        checkCategory -= 1;
                    }
                    category[checkCategory] = null;
                }
                break;
            case R.id.checkBox3:
                if(checkBox3.isChecked()) {
                    if(checkCategory < 3) {
                        category[checkCategory] = categoryName[2];
                        checkCategory += 1;
                    } else {
                        Toast.makeText(this,
                                "카테고리는 3개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show();
                        checkBox3.setChecked(false);
                    }
                }
                else {
                    if(checkCategory > 0) {
                        checkCategory -= 1;
                    }
                    category[checkCategory] = null;
                }
                break;
            case R.id.checkBox4:
                if(checkBox4.isChecked()) {
                    if(checkCategory < 3) {
                        category[checkCategory] = categoryName[3];
                        checkCategory += 1;
                    } else {
                        Toast.makeText(this,
                                "카테고리는 3개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show();
                        checkBox4.setChecked(false);
                    }
                }
                else {
                    if(checkCategory > 0) {
                        checkCategory -= 1;
                    }
                    category[checkCategory] = null;
                }
                break;
            case R.id.checkBox5:
                if(checkBox5.isChecked()) {
                    if(checkCategory < 3) {
                        category[checkCategory] = categoryName[4];
                        checkCategory += 1;
                    } else {
                        Toast.makeText(this,
                                "카테고리는 3개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show();
                        checkBox5.setChecked(false);
                    }
                }
                else {
                    if(checkCategory > 0) {
                        checkCategory -= 1;
                    }
                    category[checkCategory] = null;
                }
                break;
            case R.id.checkBox6:
                if(checkBox6.isChecked()) {
                    if(checkCategory < 3) {
                        category[checkCategory] = categoryName[5];
                        checkCategory += 1;
                    } else {
                        Toast.makeText(this,
                                "카테고리는 3개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show();
                        checkBox6.setChecked(false);
                    }
                }
                else {
                    if(checkCategory > 0) {
                        checkCategory -= 1;
                    }
                    category[checkCategory] = null;
                }
                break;
            case R.id.checkBox7:
                if(checkBox7.isChecked()) {
                    if(checkCategory < 3) {
                        category[checkCategory] = categoryName[6];
                        checkCategory += 1;
                    } else {
                        Toast.makeText(this,
                                "카테고리는 3개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show();
                        checkBox7.setChecked(false);
                    }
                }
                else {
                    if(checkCategory > 0) {
                        checkCategory -= 1;
                    }
                    category[checkCategory] = null;
                }
                break;
            case R.id.checkBox8:
                if(checkBox8.isChecked()) {
                    if(checkCategory < 3) {
                        category[checkCategory] = categoryName[7];
                        checkCategory += 1;
                    } else {
                        Toast.makeText(this,
                                "카테고리는 3개까지만 선택할 수 있습니다.", Toast.LENGTH_SHORT).show();
                        checkBox8.setChecked(false);
                    }
                }
                else {
                    if(checkCategory > 0) {
                        checkCategory -= 1;
                    }
                    category[checkCategory] = null;
                }
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
        params.put("cate1", category[0]);
        params.put("cate2", category[1]);
        params.put("cate3", category[2]);
        params.put("google_Id", "23523523");
        params.put("kakao_Id", "235235");
        String url = "http://192.168.0.109:8082/java/joinJson";
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
                Glide.with(this).load(filepath)
                        .into(imageButton);
                break;
            case 101:
                if(resultCode == RESULT_OK) {
                    filepath = FileUtils.getPath(this, data.getData());
                    Glide.with(this).load(filepath)
                            .into(imageButton);
                }
                break;
        }
    }
}