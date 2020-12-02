package com.example.ourmemory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.ourmemory.adapter.MemoryAdapter;
import com.example.ourmemory.helper.HealthJsonHelper;
import com.example.ourmemory.model.MemoryDTO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class GameListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

    HealthJsonHelper helper;
    AsyncHttpClient client;
    MemoryAdapter adapter;
    Button buttonH;
    ListView listView;
    List<MemoryDTO> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_list);

        buttonH = findViewById(R.id.buttonH);
        listView = findViewById(R.id.listView);


        list = new ArrayList<>();
        adapter = new MemoryAdapter(this, R.layout.list_item, list);

        client = new AsyncHttpClient();
        helper = new HealthJsonHelper(this, adapter, listView);

        listView.setAdapter(adapter);

        buttonH.setOnClickListener(this);
        listView.setOnItemClickListener(this);

    }
    @Override
    protected void onResume() {
        super.onResume();
        // 주석 추가한 상태로 다시 저장하자.
        adapter.clear();
        getJsonData();
    }

    private void getJsonData() {
        String url = "http://192.168.1.21:8085/java/listITJson";
        RequestParams params = new RequestParams();
        String memory_category = "Game";
        params.put("memory_category", memory_category);
        client.get(url,params, helper);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        MemoryDTO dto = list.get(position);

        Intent intent = new Intent(this, ViewActivity.class);
        intent.putExtra("dto", dto);

        intent.putExtra("memory_hit", dto.getMemory_hit()+1);
        startActivity(intent);
    }
}