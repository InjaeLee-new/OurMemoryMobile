package com.example.ourmemory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;


import com.example.ourmemory.adapter.MemoryAdapter;
import com.example.ourmemory.helper.TotalListJsonHelper;
import com.example.ourmemory.model.MemoryDTO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Index2Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    List<MemoryDTO> list;
    TotalListJsonHelper helper;
    AsyncHttpClient client;
    MemoryAdapter adapter;
    ListView listView_index;

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


    // 팝업을 위한 코드 선언
    public static boolean popUpStop = false;
    AlertDialog.Builder alert;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index2);

        // 세션관리
        sessionManager = new SessionManager(this);
//        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        session_id = user.get(sessionManager.ID);
        cate1 = user.get(sessionManager.CATE1);
        cate2 = user.get(sessionManager.CATE2);
        cate3 = user.get(sessionManager.CATE3);

        Log.d("[TEST]", session_id);
        Log.d("[TEST]", cate1);
        Log.d("[TEST]", cate2);
        Log.d("[TEST]", cate3);
        Log.d("[TEST]", ""+MemoryDTO.getTotal_count());


        // 툴바관리
        toolbar = findViewById(R.id.toolbar);
        toolBack = findViewById(R.id.toolBack);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolBack.setOnClickListener(this);

        list = new ArrayList<>();
        listView_index = findViewById(R.id.listView_index);
        adapter = new MemoryAdapter(this, R.layout.list_item, list);
        client = new AsyncHttpClient();
        helper = new TotalListJsonHelper(this, adapter, listView_index);
        listView_index.setAdapter(adapter);

        // 하단 메뉴_푸터
        btnHome = findViewById(R.id.btnHome);
        btnWrite = findViewById(R.id.btnWrite);
        btnFav = findViewById(R.id.btnFav);
        btnMypage = findViewById(R.id.btnMypage);
        btnHome.setOnClickListener(this);
        btnWrite.setOnClickListener(this);
        btnFav.setOnClickListener(this);
        btnMypage.setOnClickListener(this);

        listView_index.setOnItemClickListener(this);

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
        ActionBar ab = getSupportActionBar();

        ab.setIcon(R.drawable.ourmemory8);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
         */
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 주석 추가한 상태로 다시 저장하자.
        adapter.clear();
        getJsonData();
    }

    private void getJsonData() {
        RequestParams params = new RequestParams();
        params.put("startNum",1);
//        params.put("endNum", MemoryDTO.total_count);
        params.put("endNum", 20);
        params.put("cate1", cate1);
        params.put("cate2",cate2);
        params.put("cate3",cate3);
        String url = "http://192.168.1.21:8085/java/totalListJson";
        client.get(url, params, helper);
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MemoryDTO dto = list.get(position);

        Intent intent = new Intent(this, ViewActivity.class);
        intent.putExtra("dto", dto);

        // View에서 hit 수가 1 증가하는 부분은 다시 리스트로 돌아올때 적용된다.
        // 그래서 리스트에서 view로 넘어갈때 임의로 조회수를 1 증가시켜서 보여주기되면 바로바로 실시간 적용이 가능하다.
        // by 승원
        intent.putExtra("memory_hit", dto.getMemory_hit()+1);
        startActivity(intent);
    }
}