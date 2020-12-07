package com.example.ourmemory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ourmemory.fragment.NoticeFragment;
import com.example.ourmemory.fragment.OneByOneFragment;
import com.example.ourmemory.fragment.QuestionFragment;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {
    NoticeFragment noticeFragment;
    QuestionFragment questionFragment;
    OneByOneFragment oneByOneFragment;
    LinearLayout containerLayout;
    TextView textViewNotice, textViewOne, textViewQues;

    // 상단 툴바
    Toolbar toolbar;
    ImageButton toolBack;
    // 하단 메뉴_푸터
    ImageButton btnHome, btnWrite, btnFav, btnMypage;
    // 화면이동 전역변수 인텐트
    Intent intent;

    // 세션관리
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        // 세션관리
        sessionManager = new SessionManager(this);

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

        containerLayout = findViewById(R.id.containerLayout);
        textViewNotice = findViewById(R.id.textViewNotice);
        textViewOne = findViewById(R.id.textViewOne);
        textViewQues = findViewById(R.id.textViewQues);

        noticeFragment = new NoticeFragment(this);
        questionFragment = new QuestionFragment(this);
        oneByOneFragment = new OneByOneFragment(this);

        if (savedInstanceState ==null){
            getSupportFragmentManager().beginTransaction().add(R.id.containerLayout, noticeFragment).commit();
            textViewNotice.setTextColor(Color.parseColor("#DD7A7A"));
        }
        textViewNotice.setOnClickListener(this);
        textViewOne.setOnClickListener(this);
        textViewQues.setOnClickListener(this);

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
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textViewNotice:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, noticeFragment).commit();
                textViewNotice.setTextColor(Color.parseColor("#DD7A7A"));
                textViewQues.setTextColor(Color.parseColor("#000000"));
                textViewOne.setTextColor(Color.parseColor("#000000"));

                break;
            case R.id.textViewOne:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, oneByOneFragment).commit();
                textViewOne.setTextColor(Color.parseColor("#DD7A7A"));
                textViewNotice.setTextColor(Color.parseColor("#000000"));
                textViewQues.setTextColor(Color.parseColor("#000000"));

                break;
            case R.id.textViewQues:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, questionFragment).commit();
                textViewQues.setTextColor(Color.parseColor("#DD7A7A"));
                textViewNotice.setTextColor(Color.parseColor("#000000"));
                textViewOne.setTextColor(Color.parseColor("#000000"));
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
}