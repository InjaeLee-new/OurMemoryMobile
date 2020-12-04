package com.example.ourmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ourmemory.helper.JsonAppJoinHelper;
import com.example.ourmemory.helper.JsonJoinHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AppJoinActivity extends AppCompatActivity implements View.OnClickListener {
    Button buttonJoin, buttonBack;
    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8;

    EditText editTextName, editTextId;

    String [] categoryName = {"memory", "pet", "it", "game" , "food", "music", "art", "health"};
    String [] category = new String[3];

    int checkCategory = 0;

    JsonAppJoinHelper helper;
    AsyncHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_join);

        client = new AsyncHttpClient();
        helper = new JsonAppJoinHelper(this);

        Log.d("[TEST]", "" +checkCategory);
        if(category[checkCategory] != null) {
            Log.d("[TEST]", category[checkCategory]);
        } else {
            Log.d("[TEST]", checkCategory + "인덱스의 category값이 null입니다.");
        }

        buttonJoin = findViewById(R.id.buttonJoin);
        buttonBack = findViewById(R.id.buttonBack);
        //buttonForTest = findViewById(R.id.buttonForTest);

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

        buttonJoin.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
        //buttonForTest.setOnClickListener(this);

        checkBox1.setOnClickListener(this);
        checkBox2.setOnClickListener(this);
        checkBox3.setOnClickListener(this);
        checkBox4.setOnClickListener(this);
        checkBox5.setOnClickListener(this);
        checkBox6.setOnClickListener(this);
        checkBox7.setOnClickListener(this);
        checkBox8.setOnClickListener(this);

        Intent intent = getIntent();
        editTextName.setText(intent.getStringExtra("user_name"));
        editTextId.setText(intent.getStringExtra("user_id"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonJoin:
                String name = editTextName.getText().toString().trim();
                String id = editTextId.getText().toString().trim();

                String msg = null;
                if(msg == null && name.equals("")) {
                    msg = "이름을 입력하세요.";
                } else if (msg == null && id.equals("")){
                    msg = "아이디를 입력하세요.";
                } else if (msg == null && category[0].equals("")){
                    msg = "카테고리 1개 이상 선택하세요.";
                } if(msg != null) {
                Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
                return;
            }
                MemoryAppJoinAction();
                Intent intentBack = new Intent(this, MainActivity.class);
                intentBack.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intentBack);
                break;
            case R.id.buttonBack:
                finish();
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

    private void MemoryAppJoinAction() {
        RequestParams params = new RequestParams();
        params.put("id", editTextId.getText().toString().trim());
        params.put("cate1", category[0]);
        params.put("cate2", category[1]);
        params.put("cate3", category[2]);
        params.put("google_Id", "23523523");
        params.put("kakao_Id", "235235");
        String url = "http://192.168.1.21:8085/java/appJoinJson";
        client.post(url, params, helper);
    }

}