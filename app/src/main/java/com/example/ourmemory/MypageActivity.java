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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ourmemory.model.MemberDTO;
import com.example.ourmemory.model.RecommandDTO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cz.msebera.android.httpclient.Header;

public class MypageActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextName, editTextId, editTextNick, editTextEmail, editTextTel, editTextAddr, editTextdate, editTextCate;
    ImageView imageView1;

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

    // 서버 연동을 하기 위해 필요한 코드
    String rt = null;
    MypageHelper helper;
    AsyncHttpClient client;

    private void getJsonData() {
        RequestParams params = new RequestParams();
        params.put("id", session_id);
        String url = "http://192.168.1.21:8085/java//memberViewJson";
        client.post(url, params, helper);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        imageView1 = findViewById(R.id.imageView1);

        // 세션관리
        sessionManager = new SessionManager(this);
        //  sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        session_id = user.get(sessionManager.ID);
        cate1 = user.get(sessionManager.CATE1);
        cate2 = user.get(sessionManager.CATE2);
        cate3 = user.get(sessionManager.CATE3);

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

        // 출력될 editText
        editTextName = findViewById(R.id.editTextName);
        editTextId = findViewById(R.id.editTextId);
        editTextNick = findViewById(R.id.editTextNick);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextTel = findViewById(R.id.editTextTel);
        editTextAddr = findViewById(R.id.editTextAddr);
        editTextdate = findViewById(R.id.editTextdate);
        editTextCate = findViewById(R.id.editTextCate);

        // 서버 연동을 하기 위해 필요한 코드
        helper = new MypageHelper();
        client = new AsyncHttpClient();

        // json데이터 불러오기
        getJsonData();



    }

    class MypageHelper extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {
            String str = new String(bytes);
            try {
                JSONObject json = new JSONObject(str);
                rt = json.getString("rt");
                if(rt.equals("OK")) {
                    JSONArray memberView = json.getJSONArray("memberView");
                    JSONObject select = memberView.getJSONObject(0);
                    editTextId.setText(session_id);
                    editTextName.setText(select.getString("user_name"));
                    editTextNick.setText(select.getString("nickname"));
                    editTextEmail.setText(select.getString("email1")+"@"+select.getString("email2"));
                    editTextTel.setText(select.getString("tel1")+"-"+select.getString("tel2")+"-"+select.getString("tel3"));
                    editTextAddr.setText(select.getString("addr"));
                    editTextdate.setText(select.getString("logtime").substring(0,10));
                    editTextCate.setText(cate1+" / "+cate2+" / "+cate3);

                    // 회원가입 시 넣었던 이미지 존재 X, 실제 존재하는 이미지 이름으로 해도 출력 X
//                    String full_filename = "http://192.168.0.9:8085/java/img" + "/" + select.getString("profile_image");

                    String full_filename = "http://192.168.0.109:8082/java/img" + "/" + "turtle.png";
                    Glide.with(MypageActivity.this).load(full_filename).into(imageView1);
                }

//                for(int a = 0 ; a < memberView.length(); a++) {
//                    MemberDTO dto = new MemberDTO();
//                    dto.setTel1(temp.getString("tel1"));
//                    dto.setTel2(temp.getString("tel2"));
//                    dto.setTel3(temp.getString("tel3"));
//                    dto.setAddr(temp.getString("addr"));
//                    dto.setEmail1(temp.getString("email1"));
//                    dto.setEmail2(temp.getString("email2"));
//                    dto.setNickname(temp.getString("nickname"));
//                    dto.setLogtime(temp.getString("logtime"));
//                    dto.setProfile_image(temp.getString("profile_image"));
//                    dto.setUser_id(temp.getString("user_id"));
//                    dto.setUser_name(temp.getString("user_name"));
//                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            Toast.makeText(MypageActivity.this, "실패했는데요 ㅎ?" + i + "에러가 났네요", Toast.LENGTH_SHORT).show();
        }
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
            case R.id.action_logout:
                // 로그아웃 테스트
                // 이후 로그아웃 버튼 생성시 sessionManager.logout(); 함수 실행
                sessionManager.logout();
                finish();
                MainActivity.LoginOK =false;
                break;
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


}