package com.example.ourmemory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.ourmemory.adapter.FavoriteAdapter;
import com.example.ourmemory.adapter.MyListAdapter;
import com.example.ourmemory.helper.FavoriteJsonHelper;
import com.example.ourmemory.helper.MyListJsonHelper;
import com.example.ourmemory.model.MemoryDTO;
import com.example.ourmemory.model.RecommandDTO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FavoriteActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    List<MemoryDTO> list_myList;
    ListView listView_myList;
    MyListAdapter adapter1;
    MyListJsonHelper helper1;

    List<RecommandDTO> list_fav;
    ListView listView_fav;
    FavoriteAdapter adapter2;
    FavoriteJsonHelper helper2;

    AsyncHttpClient client1, client2;

    // 상단 툴바
    Toolbar toolbar;
    ImageButton toolBack;
    // 하단 메뉴_푸터
    ImageButton btnHome, btnWrite, btnFav, btnMypage;
    // 화면이동 전역변수 인텐트
    Intent intent;
    // 세션관리
    SessionManager sessionManager;
    String session_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

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
//        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        session_id = user.get(sessionManager.ID);


        // 리스트 뷰 관리
        list_myList = new ArrayList<MemoryDTO>();
        listView_myList = findViewById(R.id.listView_myList);
        adapter1 = new MyListAdapter(this, R.layout.mylist_item, list_myList);
        client1 = new AsyncHttpClient();
        helper1 = new MyListJsonHelper(this, adapter1, listView_myList);
        listView_myList.setAdapter(adapter1);
        listView_myList.setOnItemClickListener(this);

        // 리스트 뷰 관리
        list_fav = new ArrayList<RecommandDTO>();
        listView_fav = findViewById(R.id.listView_fav);
        adapter2 = new FavoriteAdapter(this, R.layout.favorite_item, list_fav);
        client2 = new AsyncHttpClient();
        helper2 = new FavoriteJsonHelper(this, adapter2, listView_fav);
        listView_fav.setAdapter(adapter2);
        listView_fav.setOnItemClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter1.clear();
        adapter2.clear();
        getJsonData();
    }

    private void getJsonData() {
        RequestParams params = new RequestParams();
        params.put("startNum",1);
        //MemoryDTO.getTotal_count()
        params.put("endNum", 10);
        params.put("id", session_id);
        String url = "http://192.168.1.21:8085/java/myListJson";
        client1.get(url, params, helper1);

        RequestParams params2 = new RequestParams();
        params2.put("id", session_id);
        String url2 = "http://192.168.1.21:8085/java/favoriteJson";
        client2.get(url2, params2, helper2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_actions, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_contactus:    //세팅 액티비티로 가도록 이동
                Intent intent = new Intent(this, SettingsActivity.class);
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
        MemoryDTO dto = list_myList.get(position);
        Intent intent = new Intent(this, ViewActivity.class);
        intent.putExtra("dto", dto);
            // View에서 hit 수가 1 증가하는 부분은 다시 리스트로 돌아올때 적용된다.
            // 그래서 리스트에서 view로 넘어갈때 임의로 조회수를 1 증가시켜서 보여주기되면 바로바로 실시간 적용이 가능하다.
            // by 승원
        intent.putExtra("memory_hit", dto.getMemory_hit()+1);
        startActivity(intent);


//        RecommandDTO rdto = list_fav.get(position);
//        Intent intent2 = new Intent(this, ViewActivity.class);
//        intent2.putExtra("getRecommand_seq.", rdto.getRecommand_seq());
//        // View에서 hit 수가 1 증가하는 부분은 다시 리스트로 돌아올때 적용된다.
//        // 그래서 리스트에서 view로 넘어갈때 임의로 조회수를 1 증가시켜서 보여주기되면 바로바로 실시간 적용이 가능하다.
//        // by 승원
//        intent2.putExtra("memory_hit", dto.getMemory_hit()+1);
//        startActivity(intent2);



    }
}