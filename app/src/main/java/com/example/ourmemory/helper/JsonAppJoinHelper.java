package com.example.ourmemory.helper;

import android.app.Activity;
import android.widget.Toast;

import com.example.ourmemory.AppJoinActivity;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class JsonAppJoinHelper extends AsyncHttpResponseHandler {

    Activity activity;

    public JsonAppJoinHelper(Activity activity) {
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
            String rt = json.getString("rt");
            if (rt.equals("OK")){
                Toast.makeText(activity,"가입 성공",Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(activity,"저장 실패",Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
        Toast.makeText(activity, "앱 회원가입 실패", Toast.LENGTH_SHORT).show();
    }
}
