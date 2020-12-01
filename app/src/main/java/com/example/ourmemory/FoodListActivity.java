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

import androidx.appcompat.app.AppCompatActivity;

public class FoodListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    JsonHelper helper;
    AsyncHttpClient client;
    MemoryAdapter adapter;
    Button button;
    ListView listView;
    List<MemoryDTO> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        button = findViewById(R.id.button);
        listView  = findViewById(R.id.listView);
        list = new ArrayList<>();
        adapter = new MemoryAdapter(this, R.layout.list_item, list);

        client = new AsyncHttpClient();
        helper = new JsonHelper(this, adapter, listView);

        listView.setAdapter(adapter);
        getJsonData();

        button.setOnClickListener(this);
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
        String url = "http://192.168.1.21:8085/java/foodListJson";
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

        // View에서 hit 수가 1 증가하는 부분은 다시 리스트로 돌아올때 적용된다.
        // 그래서 리스트에서 view로 넘어갈때 임의로 조회수를 1 증가시켜서 보여주기되면 바로바로 실시간 적용이 가능하다.
        // by 승원
        intent.putExtra("memory_hit", dto.getMemory_hit()+1);
        startActivity(intent);
    }





}