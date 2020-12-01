package com.example.ourmemory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.ourmemory.adapter.MemoryAdapter;
import com.example.ourmemory.helper.TotalListJsonHelper;
import com.example.ourmemory.model.MemoryDTO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class Index2Activity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    List<MemoryDTO> list;
    TotalListJsonHelper helper;
    AsyncHttpClient client;
    MemoryAdapter adapter;
    ListView listView_index;
    ImageButton btnHome, btnWrite, btnFav, btnTotal;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index2);

        list = new ArrayList<>();
        listView_index = findViewById(R.id.listView_index);
        adapter = new MemoryAdapter(this, R.layout.list_item, list);
        client = new AsyncHttpClient();
        helper = new TotalListJsonHelper(this, adapter, listView_index);
        listView_index.setAdapter(adapter);

        btnHome = findViewById(R.id.btnHome);
        btnWrite = findViewById(R.id.btnWrite);
        btnFav = findViewById(R.id.btnFav);
        btnTotal = findViewById(R.id.btnTotal);

        btnHome.setOnClickListener(this);
        btnWrite.setOnClickListener(this);
        btnFav.setOnClickListener(this);
        btnTotal.setOnClickListener(this);

        ActionBar ab = getSupportActionBar();

        ab.setIcon(R.drawable.ourmemory8);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
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
        params.put("endNum",MemoryDTO.getTotal_count());
        params.put("cate1","health");
        params.put("cate2","pet");
        params.put("cate3","food");
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
        // 인텐트는 우선 테스트용으로 다 동일하게 넣어둠

        switch (item.getItemId()) {
            case R.id.action_search:
                Intent intent1 = new Intent(this, ListActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent1);
                break;
            case R.id.action_favorite:
                Intent intent2 = new Intent(this, ListActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent2);
                break;
            case R.id.action_mypage:
                Intent intent3 = new Intent(this, ListActivity.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent3);
                break;
            case R.id.action_logout:
                Intent intent4 = new Intent(this, ListActivity.class);
                intent4.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent4);
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
                case R.id.btnFav:       // 좋아요 누른 게시물만!
                    break;
                case R.id.btnTotal:     // 내 게시물?
                    intent = new Intent(this, TotalListActivity.class);
                    startActivity(intent);
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