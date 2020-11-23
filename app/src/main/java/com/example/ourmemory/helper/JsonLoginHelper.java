package com.example.ourmemory.helper;

import android.app.Activity;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ourmemory.MainActivity;
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


    public JsonLoginHelper(Activity activity) {
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

            if(result > 0 ) {
                MainActivity.LoginOK = true;
                MainActivity.user_name = json.getString("user_name");
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
