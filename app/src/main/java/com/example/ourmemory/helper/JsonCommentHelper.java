package com.example.ourmemory.helper;

import android.app.Activity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ourmemory.adapter.MemoryCommentAdapter;
import com.example.ourmemory.model.MemoryCommentDTO;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

// 댓글 정보를 불러오는 helper
public class JsonCommentHelper extends AsyncHttpResponseHandler {
    Activity activity;
    MemoryCommentAdapter adapter;
    ListView listView;


    public JsonCommentHelper(Activity activity, MemoryCommentAdapter adapter, ListView listView) {
        this.activity = activity;
        this.adapter = adapter;
        this.listView = listView;
    }

    @Override
    public void onStart() {
        super.onStart();
//        listView.setSelection(adapter.getCount() - 1);
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
            JSONArray memoryCommentList = json.getJSONArray("memoryCommentList");

            for(int a = 0 ; a < memoryCommentList.length(); a++) {
                JSONObject temp = memoryCommentList.getJSONObject(a);
                MemoryCommentDTO memoryCommentDTO = new MemoryCommentDTO();
                memoryCommentDTO.setMemory_seq(temp.getInt("memory_seq"));
                memoryCommentDTO.setMemory_comment_name(temp.getString("memory_comment_name"));
                memoryCommentDTO.setMemory_comment_content(temp.getString("memory_comment_content"));
                memoryCommentDTO.setReg_date(temp.getString("reg_date"));

                adapter.add(memoryCommentDTO);
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
