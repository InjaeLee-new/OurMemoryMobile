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

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class HealthListActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener {
    HealthJsonHelper helper;
    AsyncHttpClient client;
    MemoryAdapter adapter;
    Button buttonH;
    ListView listViewH;
    List<MemoryDTO> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_list);

        buttonH = findViewById(R.id.buttonH);
        listViewH = findViewById(R.id.listViewH);


        list = new ArrayList<>();
        adapter = new MemoryAdapter(this, R.layout.list_item, list);

        client = new AsyncHttpClient();
        helper = new HealthJsonHelper(this, adapter, listViewH);

        listViewH.setAdapter(adapter);

        buttonH.setOnClickListener(this);
        listViewH.setOnItemClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
        // 주석 추가한 상태로 다시 저장하자.
        adapter.clear();
        getJsonData();
    }

    private void getJsonData() {
        String url = "http://192.168.0.109:8082/java/healthListJson";
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
    }//

}