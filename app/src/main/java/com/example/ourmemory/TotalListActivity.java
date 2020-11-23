package com.example.ourmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.ourmemory.adapter.MemoryAdapter;
import com.example.ourmemory.helper.HealthJsonHelper;
import com.example.ourmemory.helper.TotalListJsonHelper;
import com.example.ourmemory.model.MemoryDTO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

public class TotalListActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener {
    TotalListJsonHelper helper;
    AsyncHttpClient client;
    MemoryAdapter adapter;
    Button buttonTotal;
    ListView listViewTotal;
    List<MemoryDTO> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_list);
        buttonTotal = findViewById(R.id.buttonTotal);
        listViewTotal = findViewById(R.id.listViewTotal);


        list = new ArrayList<>();
        adapter = new MemoryAdapter(this, R.layout.list_item, list);

        client = new AsyncHttpClient();
        helper = new TotalListJsonHelper(this, adapter, listViewTotal);

        listViewTotal.setAdapter(adapter);

        buttonTotal.setOnClickListener(this);
        listViewTotal.setOnItemClickListener(this);
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
        String url = "http://192.168.0.42:8088/java/totalListJson";
        client.get(url, params, helper);
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