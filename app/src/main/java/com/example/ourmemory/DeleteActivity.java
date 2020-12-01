package com.example.ourmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ourmemory.model.MemoryDTO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DeleteActivity extends AppCompatActivity implements View.OnClickListener {

    MemoryDTO memoryDTO;
    TextView textViewDel;
    Button buttonCB, buttonDel;
    AsyncHttpClient client;
    DeleteResponse response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        textViewDel = findViewById(R.id.textViewDel);
        buttonCB = findViewById(R.id.buttonCB);
        buttonDel = findViewById(R.id.buttonDel);

        memoryDTO = (MemoryDTO) getIntent().getSerializableExtra("dto");
        client =  new AsyncHttpClient();
        response = new DeleteResponse();

        textViewDel.setText(memoryDTO.getMemory_num() + "번을 삭제하시겠습니까?");

        buttonCB.setOnClickListener(this);
        buttonDel.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonCB:
                finish();
                break;
            case R.id.buttonDel:
                String url = "http://192.168.1.21:8085/java/DeleteJson";
                RequestParams params = new RequestParams();
                params.put("memory_num", memoryDTO.getMemory_num());
                client.post(url, params, response);
                break;
        }
    }
    class DeleteResponse extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
            String str = new String(responseBody);
            try {
                JSONObject json = new JSONObject(str);
                String rt = json.getString("rt");
                if(rt.equals("성공!")) {
                    Toast.makeText(DeleteActivity.this, "삭제성공", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DeleteActivity.this, "삭제실패", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            Toast.makeText(DeleteActivity.this, "서버 연결 실패", Toast.LENGTH_SHORT).show();
        }
    }

}