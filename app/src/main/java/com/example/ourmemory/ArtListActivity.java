package com.example.ourmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.ourmemory.adapter.MemoryAdapter;
import com.example.ourmemory.helper.JsonHelper;
import com.example.ourmemory.model.MemoryDTO;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;
import java.util.List;

public class ArtListActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener{
    JsonHelper helper;
    AsyncHttpClient client;
    MemoryAdapter adapter;
    Button buttonA;
    ListView listViewArt;
    List<MemoryDTO> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_art_list);
        buttonA = findViewById(R.id.buttonA);
        listViewArt = findViewById(R.id.listViewArt);


        list = new ArrayList<>();
        adapter = new MemoryAdapter(this, R.layout.list_item, list);

        client = new AsyncHttpClient();
        helper = new JsonHelper(this, adapter, listViewArt);

        listViewArt.setAdapter(adapter);

        buttonA.setOnClickListener(this);
        listViewArt.setOnItemClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 주석 추가한 상태로 다시 저장하자.
        adapter.clear();
        getJsonData();
    }

    private void getJsonData() {
        String url = "http://192.168.1.21:8085/java/artListJson";

        client.get(url, helper);
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