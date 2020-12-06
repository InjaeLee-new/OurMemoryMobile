package com.example.ourmemory.helper;

import android.app.Activity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ourmemory.adapter.FavoriteAdapter;
import com.example.ourmemory.adapter.MemoryAdapter;
import com.example.ourmemory.model.RecommandDTO;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class FavoriteJsonHelper extends AsyncHttpResponseHandler {
    Activity activity;
    FavoriteAdapter adapter;
    ListView listView;

    public FavoriteJsonHelper(Activity activity, FavoriteAdapter adapter, ListView listView) {
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
//            MemoryDTO.setTotal_count(json.getInt("totalResult"));
            JSONArray memorylist = json.getJSONArray("memoryList");

            for(int a = 0 ; a < memorylist.length(); a++) {
                JSONObject temp = memorylist.getJSONObject(a);

                RecommandDTO dto = new RecommandDTO();
                dto.setRecommand_id(temp.getString("recommand_id"));
                dto.setRecommand_seq(temp.getInt("recommand_seq"));

                adapter.add(dto);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
        Toast.makeText(activity, "실패 , i =" + i, Toast.LENGTH_SHORT).show();
    }
}
