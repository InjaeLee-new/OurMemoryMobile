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
import com.example.ourmemory.helper.JsonHelper;
import com.example.ourmemory.model.ContactUsDTO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class QuestionFragment extends Fragment {
    Context context;
    View rootView;
    ContactUsActivity activity;
    AsyncHttpClient client;
    RecyclerView recyclerView;
    ContactRecyclerAdapter adapter;
    ContactUsJsonHelper helper;
    ArrayList<ContactUsDTO> list;
    public QuestionFragment(Context context) {
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_question, container, false);
        recyclerView = rootView.findViewById(R.id.recyclerViewQ);

        activity = new ContactUsActivity();
        client = new AsyncHttpClient();
        list = new ArrayList<>();
        adapter = new ContactRecyclerAdapter(list, context);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(linearLayoutManager);

        helper = new ContactUsJsonHelper(activity, recyclerView, adapter);

        getJsonData();

        recyclerView.setAdapter(adapter);
        return rootView;
    }

    private void getJsonData() {
<<<<<<< HEAD
        String url = "http://192.168.1.21:8085/java/questionJson";
=======
        String url = "http://192.168.0.109:8082/java/questionJson";
>>>>>>> 68b5db84e9d059b58f9069b020e1d51b8c9339c2
        client.get(url, helper);
        adapter.notifyDataSetChanged();
    }

}