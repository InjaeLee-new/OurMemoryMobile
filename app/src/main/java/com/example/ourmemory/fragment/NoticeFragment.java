package com.example.ourmemory.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ourmemory.ContactUsActivity;
import com.example.ourmemory.R;
import com.example.ourmemory.adapter.ContactRecyclerAdapter;
import com.example.ourmemory.helper.ContactUsJsonHelper;
import com.example.ourmemory.model.ContactUsDTO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class NoticeFragment extends Fragment {
    View rootView;
    Context context;
    ContactUsActivity activity;
    AsyncHttpClient client;
    ContactUsJsonHelper helper;
    RecyclerView recyclerViewN;
    ContactRecyclerAdapter adapter;
    ArrayList<ContactUsDTO> list;
    public NoticeFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notice, container, false);
        activity = new ContactUsActivity();

        recyclerViewN = rootView.findViewById(R.id.recyclerViewN);
        recyclerViewN.setHasFixedSize(true); // 리사이클러뷰 기존 성능 강화
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerViewN.setLayoutManager(linearLayoutManager);

        list = new ArrayList<>();

        client = new AsyncHttpClient();
        adapter = new ContactRecyclerAdapter(list, context);

        helper = new ContactUsJsonHelper(activity,recyclerViewN,adapter);
        getJsonData();

        recyclerViewN.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        list.clear();
    }

    private void getJsonData() {
        String url = "http://192.168.0.9:8085/java/noticeJson";
        client.get(url, helper);
    }
}