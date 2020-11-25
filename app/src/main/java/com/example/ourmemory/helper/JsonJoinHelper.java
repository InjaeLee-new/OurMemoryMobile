package com.example.ourmemory.helper;

import android.app.Activity;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class JsonJoinHelper extends AsyncHttpResponseHandler {

    Activity activity;
    int result;

    public JsonJoinHelper(Activity activity) {
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
//            result = Integer.parseInt(json.getString("rt"));
            Toast.makeText(activity,"회원가입 성공 + result = " + result, Toast.LENGTH_SHORT).show();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
        Toast.makeText(activity, "회원가입 실패, result = " + result  + i, Toast.LENGTH_SHORT).show();
    }
}
