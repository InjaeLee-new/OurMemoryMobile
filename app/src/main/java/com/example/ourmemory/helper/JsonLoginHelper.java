package com.example.ourmemory.helper;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ourmemory.MainActivity;
import com.example.ourmemory.SessionManager;
import com.example.ourmemory.adapter.MemoryAdapter;
import com.example.ourmemory.model.MemoryDTO;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


// 로그인 관련 static 데이터 공유를 위한 helper
public class JsonLoginHelper extends AsyncHttpResponseHandler {
    Activity activity;
    SessionManager sessionManager;

    public JsonLoginHelper(Activity activity, SessionManager sessionManager) {
        this.sessionManager = sessionManager;
        this.activity = activity;
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
            int result = Integer.parseInt(json.getString("result"));

            JSONArray cateList = json.getJSONArray("cateList");

            if(result > 0 ) {
                for(int a = 0 ; a < cateList.length(); a++) {
                    JSONObject temp = cateList.getJSONObject(a);
                    MainActivity.LoginOK = true;
                    String user_name = temp.getString("user_name");
                    String user_id = temp.getString("user_id");
                    String cate1 = temp.getString("cate1");
                    String cate2 = temp.getString("cate2");
                    String cate3 = temp.getString("cate3");
                    String google_Id = temp.getString("google_Id");
                    String kakao_Id = temp.getString("kakao_Id");

                    if(cate1 == null) {
                        MainActivity.isntAppJoin = true;
                    }

                    sessionManager.createSession(user_id, user_name, cate1,
                            cate2, cate3, google_Id, kakao_Id);
                }

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
        Toast.makeText(activity, "로그인 실패" + i, Toast.LENGTH_SHORT).show();
    }
}
