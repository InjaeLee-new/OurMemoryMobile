package com.example.ourmemory;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.ourmemory.adapter.FavoriteAdapter;
import com.example.ourmemory.adapter.MemoryAdapter;
import com.example.ourmemory.adapter.MyListAdapter;
import com.example.ourmemory.helper.FavoriteJsonHelper;
import com.example.ourmemory.helper.MyListJsonHelper;
import com.example.ourmemory.helper.TotalListJsonHelper;
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
        params.put("endNum", 5);
        params.put("id", session_id);
        String url = "http://192.168.0.109:8082/java/myListJson";
        client1.get(url, params, helper1);

        RequestParams params2 = new RequestParams();
        params2.put("id", session_id);
        String url2 = "http://192.168.0.109:8082/java/favoriteJson";
        client2.get(url2, params2, helper2);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}