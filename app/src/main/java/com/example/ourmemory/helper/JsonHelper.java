package com.example.ourmemory.helper;

import android.app.Activity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ourmemory.adapter.MemoryAdapter;
import com.example.ourmemory.model.MemoryDTO;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class JsonHelper extends AsyncHttpResponseHandler {
    Activity activity;
    MemoryAdapter adapter;
    ListView listView;


    public JsonHelper(Activity activity, MemoryAdapter adapter, ListView listView) {
        this.activity = activity;
        this.adapter = adapter;
        this.listView = listView;
    }

    @Override
    public void onStart() {
        super.onStart();
        listView.setSelection(adapter.getCount() - 1);
    }

    @Override
    public void onFinish() {
        super.onFinish();
    }

    @Override
    public void onSuccess(int i, Header[] headers, byte[] bytes) {
        String str = new String(bytes);

        try {
            JSONObject json = new JSONObject(str);
            JSONArray memorylist = json.getJSONArray("memoryList");

            for(int a = 0 ; a < memorylist.length(); a++) {
                JSONObject temp = memorylist.getJSONObject(a);
                MemoryDTO memoryDTO = new MemoryDTO();
                memoryDTO.setMemory_num(temp.getInt("memory_num"));
                memoryDTO.setMemory_file(temp.getString("memory_file"));
                memoryDTO.setMemory_subject(temp.getString("memory_subject"));
                memoryDTO.setMemory_content(temp.getString("memory_content"));
                memoryDTO.setMemory_date(temp.getString("memory_date"));
                memoryDTO.setMemory_hit(temp.getInt("memory_hit"));
                memoryDTO.setMemory_rec(temp.getInt("memory_rec"));
                memoryDTO.setMemory_nrec(temp.getInt("memory_nrec"));
                memoryDTO.setMemory_name(temp.getString("memory_name"));
//                memoryDTO.setMemory_category(temp.getString("memory_category"));

                adapter.add(memoryDTO);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
        Toast.makeText(activity, "오류떴는데용 ㅎㅎㅎ" + i, Toast.LENGTH_SHORT).show();
    }
}
