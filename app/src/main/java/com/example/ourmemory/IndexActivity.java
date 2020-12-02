package com.example.ourmemory;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.EventLog;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toolbar;


import com.bumptech.glide.Glide;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 하단에 onClickListener 부분에 주석으로 해야할 내용들을 남겨놨습니다.
     * 아래쪽으로 가서 확인해주세요.
     */
    TextView textView6;

    Button buttonPet, buttonLogout;

    Button buttonFood , buttonHealth, buttonGame, buttonTotal;
    Button buttonTravel, buttonMusic, buttonArt, buttonIt;

    Toolbar toolbar;

    // 팝업을 위한 코드 선언
    public static boolean popUpStop = false;
    AlertDialog.Builder alert;

    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        sessionManager = new SessionManager(this);
//        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        String session_id = user.get(sessionManager.ID);
        String cate1 = user.get(sessionManager.CATE1);
        String google = user.get(sessionManager.GOOGLE_ID);
        textView6 = findViewById(R.id.textView6);

        textView6.setText("카데고리 목록 (안녕하세요 " + session_id + "\n" + cate1 + "\n" + google + "\n" + "님)");


        buttonPet = findViewById(R.id.buttonPet);
        buttonFood = findViewById(R.id.buttonFood);
        buttonHealth = findViewById(R.id.buttonHealth);
        buttonGame = findViewById(R.id.buttonGame);
        buttonTravel = findViewById(R.id.buttonTravel);
        buttonMusic = findViewById(R.id.buttonMusic);
        buttonArt = findViewById(R.id.buttonArt);
        buttonIt = findViewById(R.id.buttonIt);
        buttonTotal = findViewById(R.id.buttonTotal);
        buttonLogout = findViewById(R.id.buttonLogout);

        buttonPet.setOnClickListener(this);
        buttonFood.setOnClickListener(this);
        buttonHealth.setOnClickListener(this);
        buttonGame.setOnClickListener(this);
        buttonTravel.setOnClickListener(this);
        buttonMusic.setOnClickListener(this);
        buttonArt.setOnClickListener(this);
        buttonIt.setOnClickListener(this);
        buttonTotal.setOnClickListener(this);
        buttonLogout.setOnClickListener(this);

        // 이벤트 알림창 띄우기
        alert = new AlertDialog.Builder(this);
        LayoutInflater factory = LayoutInflater.from(this);
        final View view = factory.inflate(R.layout.eventdialog, null);
//        String full_filename = "http://192.168.1.21:8085/java/eventimage/event1.jpg";
//        Glide.with(this).load(full_filename)
//                .into(imageView);
        alert.setView(view);

        // 이벤트 팝업을 클릭하면 이벤트 페이지로 이동
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentEvent = new Intent(getApplicationContext(), EventActivity.class);
                startActivity(intentEvent);
            }
        });

        // 확인 버튼 설정
        alert.setPositiveButton("오늘 하루 보지 않기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                popUpStop = true;
            }
        });

//        WindowManager.LayoutParams params = alert.getWindow().getAttributes();
//        params.width = params.MATCH_PARENT;
//        params.height = params.MATCH_PARENT;
//        alert.getWindow().setAttributes(
//                (android.view.WindowManager.LayoutParams)
//                        params);

        if(popUpStop) {
            Handler mHandler = new Handler();
            mHandler.postDelayed(new Runnable()  {
                public void run() {
                    // 시간 지난 후 실행할 코딩
                    alert.show();
                }
            }, 500); // 0.5초후
        } else {
            alert.show();
        }

        /*
        toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ourmemory8);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        //ab.hide();
        ab.setIcon(R.drawable.ourmemory8);
        //ab.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);

         */
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_actions, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:    //마이페이지 액티비티(임시)로 가도록 이동
                Intent intent = new Intent(this, MypageActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent);
                break;
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
                Intent intent2 = new Intent(this, ListActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent2);
                break;
            case R.id.it :
                Intent intent3 = new Intent(this, ListActivity.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent3);
                break;
            case R.id.game :
                Intent intent4 = new Intent(this, ListActivity.class);
                intent4.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent4);
                break;
            case R.id.food :
                Intent intent5 = new Intent(this, ListActivity.class);
                intent5.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent5);
                break;
            case R.id.music :
                Intent intent6 = new Intent(this, ListActivity.class);
                intent6.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent6);
                break;
            case R.id.art :
                Intent intent7 = new Intent(this, ListActivity.class);
                intent7.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent7);
                break;
            case R.id.health :
                Intent intent8 = new Intent(this, ListActivity.class);
                intent8.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent8);
                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonTravel:
                Intent intent = new Intent(this, ListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent);
                break;
            /** 각자 필요한 인텐트까지만해서 작업해주면돼요. 크게 코드는 이렇게 구분됩니다.
             *  Intent intent = new Intent(시작 엑티비티 , 이동할 엑티비티);
             *  startActivity(intent);
             *  일반적으로 시작 엑티비티는 this로 현재 엑티비티를 설정하면 되는거고
             *  이동할 엑티비티는 본인들이 만든 액티비티로 이동시키면 됩니다.
             *
             *  위의 코드를 확인하면 buttonToMemoryList 버튼에 대한 이벤트 처리가 되어있는데,
             *  해당 코드를 참고해서 아래 case를 자기꺼에 맞게 처리하시면 됩니다.
             */

            case R.id.buttonArt:
                // 문화 category.
                Intent intentArt = new Intent(this, ArtListActivity.class);
                intentArt.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intentArt);
                break;

            case R.id.buttonPet:
                // 반려동물!
                Intent intentPet = new Intent(this, PetListActivity.class);
                intentPet.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intentPet);
                break;
            case R.id.buttonFood:
                // 성인누나가 새롭게 Activity 만들어서 여기서 이동시키면돼.
                Intent intentSIFood = new Intent(this, FoodListActivity.class);
                intentSIFood.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intentSIFood);
                break;
            case R.id.buttonHealth:
                // 승원이도 하나 Activity 만들어서 이동시켜주자.
                Intent intentSW = new Intent(this, HealthListActivity.class);
                intentSW.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intentSW);
                break;
            case R.id.buttonGame:
                // 세번쓰기 힘들다 성빈아 만들어서 이동시켜주자.
                Intent intentGame = new Intent(this, GameListActivity.class);
                intentGame.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intentGame);
                break;
            case R.id.buttonMusic:
                // 음악 category.
                Intent intentMusic = new Intent(this, MusicListActivity.class);
                intentMusic.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intentMusic);
                break;
            case R.id.buttonIt:
                // IT category.
                Intent intentIt = new Intent(this, ItListActivity.class);
                intentIt.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intentIt);
                break;
            case R.id.buttonTotal:
                // 선택한 카테고리 모아보기
                Intent intentTotal = new Intent(this, TotalListActivity.class);
                intentTotal.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intentTotal);
                break;
            case R.id.buttonLogout:
                // 로그아웃 테스트
                // 이후 로그아웃 버튼 생성시 sessionManager.logout(); 함수 실행
                sessionManager.logout();
                finish();
                MainActivity.LoginOK =false;
                break;
        }
    }
}