package com.example.ourmemory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ourmemory.model.MemoryDTO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class ModifyActivity extends AppCompatActivity implements View.OnClickListener , RadioGroup.OnCheckedChangeListener {
    TextView textViewNum;
    EditText editTextS,editTextC;

    RadioGroup radioGroup;
    RadioButton radioButton1, radioButton2, radioButton3, radioButton4, radioButton5, radioButton6, radioButton7, radioButton8;
    MemoryDTO memoryDTO;

    HttpResponse response;

    Button buttonBK, buttonSave;

    AsyncHttpClient client;

    String memory_category = "memory";

    // 상단 툴바
    Toolbar toolbar;
    ImageButton toolBack;
    // 하단 메뉴_푸터
    ImageButton btnHome, btnWrite, btnFav, btnMypage;
    // 화면이동 전역변수 인텐트
    Intent intent;
    // 세션관리
    SessionManager sessionManager;
    String session_id, cate1, cate2, cate3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);

        // 툴바관리
        toolbar = findViewById(R.id.toolbar);
        toolBack = findViewById(R.id.toolBack);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolBack.setOnClickListener(this);

        // 하단 메뉴_푸터
        btnHome = findViewById(R.id.btnHome);
        btnWrite = findViewById(R.id.btnWrite);
        btnFav = findViewById(R.id.btnFav);
        btnMypage = findViewById(R.id.btnMypage);
        btnHome.setOnClickListener(this);
        btnWrite.setOnClickListener(this);
        btnFav.setOnClickListener(this);
        btnMypage.setOnClickListener(this);

        // 세션관리
        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        session_id = user.get(sessionManager.ID);
        cate1 = user.get(sessionManager.CATE1);
        cate2 = user.get(sessionManager.CATE2);
        cate3 = user.get(sessionManager.CATE3);


        radioGroup = findViewById(R.id.rd);
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioButton4 = findViewById(R.id.radioButton4);
        radioButton5 = findViewById(R.id.radioButton5);
        radioButton6 = findViewById(R.id.radioButton6);
        radioButton7 = findViewById(R.id.radioButton7);
        radioButton8 = findViewById(R.id.radioButton8);

        editTextS = findViewById(R.id.editTextS);
        editTextC = findViewById(R.id.editTextC);

        buttonBK = findViewById(R.id.buttonBK);
        buttonSave = findViewById(R.id.buttonSave);
        textViewNum = findViewById(R.id.textViewNum);


        memoryDTO = (MemoryDTO) getIntent().getSerializableExtra("dto");

        textViewNum.setText(memoryDTO.getMemory_num() + "번 글 수정 \uD83D\uDCDD");

        editTextS.setText(memoryDTO.getMemory_subject());
        editTextC.setText(memoryDTO.getMemory_content());
        radioGroup.setOnCheckedChangeListener(this);

        response = new HttpResponse();
        client = new AsyncHttpClient();

        buttonBK.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
        radioGroup.setOnCheckedChangeListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonBK:
                finish();
                break;
            case R.id.buttonSave:
                updateJson();
                break;
            case R.id.btnHome:      // 홈화면 (모든 리스트 보이는 현재 화면)
                intent = new Intent(this, Index2Activity.class);
                startActivity(intent);
                break;
            case R.id.btnWrite:     // 작성화면 (WriteActivity)
                intent = new Intent(this, WriteActivity.class);
                startActivity(intent);
                break;
            case R.id.btnFav:       // 좋아요/비추천 누른 게시물만
                intent = new Intent(this, FavoriteActivity.class);
                startActivity(intent);
                break;
            case R.id.btnMypage:     // 마이페이지
                intent = new Intent(this, MypageActivity.class);
                startActivity(intent);
                break;

            case R.id.toolBack :
                finish();
                break;
        }
    }

    private void updateJson() {
        String url = "http://192.168.0.109:8082/java/modifyJson";
        RequestParams params = new RequestParams();
        params.put("memory_num", memoryDTO.getMemory_num());
        params.put("memory_subject", editTextS.getText().toString().trim());
        params.put("memory_content", editTextC.getText().toString().trim());
        params.put("memory_category", memory_category);

        Toast.makeText(this, "" + memoryDTO.getMemory_num()
                + editTextC.getText() + editTextS.getText() + memoryDTO.getMemory_category(), Toast.LENGTH_SHORT).show();
        client.post(url, params, response);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_actions, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
//            case R.id.action_contactus:    //세팅 액티비티로 가도록 이동
//                Intent intent = new Intent(this, SettingsActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                startActivity(intent);
//                break;
            case R.id.memory :
                Intent intent1 = new Intent(this, ListActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent1);
                break;
            case R.id.pet :
                Intent intent2 = new Intent(this, PetListActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent2);
                break;
            case R.id.it :
                Intent intent3 = new Intent(this, ItListActivity.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent3);
                break;

            case R.id.action_logout:
                sessionManager.logout();
                Intent intent10 = new Intent(this, MainActivity.class);
                intent10.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent10);
                MainActivity.LoginOK =false;
                break;

            case R.id.game :
                Intent intent4 = new Intent(this, GameListActivity.class);
                intent4.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

                startActivity(intent4);
                break;
            case R.id.food :
                Intent intent5 = new Intent(this, FoodListActivity.class);
                intent5.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent5);
                break;
            case R.id.music :
                Intent intent6 = new Intent(this, MusicListActivity.class);
                intent6.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent6);
                break;
            case R.id.art :
                Intent intent7 = new Intent(this, ArtListActivity.class);
                intent7.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent7);
                break;
            case R.id.health :
                Intent intent8 = new Intent(this, HealthListActivity.class);
                intent8.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent8);
                break;
            case R.id.action_contactus: // 고객센터 관련 activity로 이동
                Intent intent9 = new Intent(this, ContactUsActivity.class);
                intent9.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent9);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radioButton1:
                memory_category = "memory";
                Toast.makeText(this,memory_category, Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioButton2:
                memory_category = "pet";
                Toast.makeText(this,memory_category, Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioButton3:
                memory_category = "it";
                Toast.makeText(this,memory_category, Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioButton4:
                memory_category = "game";
                Toast.makeText(this,memory_category, Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioButton5:
                memory_category = "food";
                Toast.makeText(this,memory_category, Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioButton6:
                memory_category = "music";
                Toast.makeText(this,memory_category, Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioButton7:
                memory_category = "art";
                Toast.makeText(this,memory_category, Toast.LENGTH_SHORT).show();
                break;
            case R.id.radioButton8:
                memory_category = "health";
                Toast.makeText(this,memory_category, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    class HttpResponse extends AsyncHttpResponseHandler {
        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                String rt = json.getString("rt");
                if(rt.equals("성공!")) {
                    Toast.makeText(ModifyActivity.this, "수정 성공", Toast.LENGTH_SHORT).show();
                    finish(); // 현재 액티비티 종료 -> 메인으로 돌아가기
                } else {
                    Toast.makeText(ModifyActivity.this, "수정 실패", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(ModifyActivity.this, "통신 실패 statusCode = " + statusCode, Toast.LENGTH_SHORT).show();
        }
    }

}