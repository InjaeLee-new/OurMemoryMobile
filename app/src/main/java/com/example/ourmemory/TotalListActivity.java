package com.example.ourmemory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.ourmemory.adapter.MemoryAdapter;
import com.example.ourmemory.helper.TotalListJsonHelper;
import com.example.ourmemory.model.MemoryDTO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class TotalListActivity extends AppCompatActivity
        implements View.OnClickListener, AdapterView.OnItemClickListener {
    TotalListJsonHelper helper;
    AsyncHttpClient client;
    MemoryAdapter adapter;
    Button buttonTotal, buttonWrite;
    ListView listViewTotal;
    List<MemoryDTO> list;

    SessionManager sessionManager;
    String cate1, cate2, cate3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_total_list);
        buttonTotal = findViewById(R.id.buttonTotal);
        buttonWrite = findViewById(R.id.buttonWrite);

        listViewTotal = findViewById(R.id.listViewTotal);

        sessionManager = new SessionManager(this);

        HashMap<String, String> user = sessionManager.getUserDetail();
        cate1 = user.get(sessionManager.CATE1);
        cate2 = user.get(sessionManager.CATE2);
        cate3 = user.get(sessionManager.CATE3);

        list = new ArrayList<>();
        adapter = new MemoryAdapter(this, R.layout.list_item, list);

        client = new AsyncHttpClient();
        helper = new TotalListJsonHelper(this, adapter, listViewTotal);

        listViewTotal.setAdapter(adapter);

        buttonTotal.setOnClickListener(this);
        buttonWrite.setOnClickListener(this);

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
        params.put("endNum", MemoryDTO.getTotal_count());
        params.put("cate1", cate1);
        params.put("cate2", cate2);
        params.put("cate3", cate3);
//        String url = "http://192.168.1.3:8085/java/totalListJson";
//        String url = "http://192.168.1.3:8085/java/totalListJson";
        String url = "http://192.168.0.9:8085/java/totalListJson";
        client.get(url, params, helper);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonWrite: // 글 쓰기 버튼
                Intent intent = new Intent(this, WriteActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent);
                break;
            case R.id.buttonTotal: // 뒤로가기 버튼
                finish();
                break;

        }
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