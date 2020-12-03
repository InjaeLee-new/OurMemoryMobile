package com.example.ourmemory.helper;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ourmemory.adapter.ContactRecyclerAdapter;
import com.example.ourmemory.model.ContactUsDTO;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class ContactUsJsonHelper extends AsyncHttpResponseHandler {
    Activity activity;
    RecyclerView recyclerView;
    ContactRecyclerAdapter adapter;

    public ContactUsJsonHelper(Activity activity, RecyclerView recyclerView, ContactRecyclerAdapter adapter) {
        this.activity = activity;
        this.recyclerView = recyclerView;
        this.adapter = adapter;
    }
    @Override
    public void onStart() {
        super.onStart();
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
            JSONArray noticeList = json.getJSONArray("noticeList");

            for(int a = 0 ; a < noticeList.length(); a++) {
                JSONObject temp = noticeList.getJSONObject(a);

                ContactUsDTO contactUsDTO = new ContactUsDTO();

                contactUsDTO.setContactUs_num(temp.getInt("num"));
                contactUsDTO.setContactUs_subject(temp.getString("subject"));
                contactUsDTO.setContactUs_content(temp.getString("content"));
                contactUsDTO.setContactUs_logtime(temp.getString("logtime"));

                adapter.add(contactUsDTO);
            }
            adapter.notifyDataSetChanged(); // 리스트 저장 및 새로고침
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
        Toast.makeText(activity, "recycler error" + i, Toast.LENGTH_SHORT).show();
    }
}
