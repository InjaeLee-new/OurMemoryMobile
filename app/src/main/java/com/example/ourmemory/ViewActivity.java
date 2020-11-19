package com.example.ourmemory;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ourmemory.adapter.MemoryAdapter;
import com.example.ourmemory.helper.JsonHelper;
import com.example.ourmemory.model.MemoryDTO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener {

    // 서버 연동을 하기 위해 필요한 코드
    ViewHelper helper;
    AsyncHttpClient client;
    String rt = null;

    MemoryDTO memoryDTO;
    ImageView imageView;
    TextView textView1, textView2, textView3, textViewContent;
    Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        helper = new ViewHelper();
        client = new AsyncHttpClient();



        memoryDTO = (MemoryDTO) getIntent().getSerializableExtra("dto");
        getJsonData(); // 제이슨 데이터 처리!

        memoryDTO = (MemoryDTO) getIntent().getSerializableExtra("dto");

        // 1 증가한 조회수를 미리 받아버리기~
        int update_hit = getIntent().getIntExtra("memory_hit", 0);

        buttonBack = findViewById(R.id.buttonBack);
        imageView = findViewById(R.id.imageView);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textViewContent = findViewById(R.id.textViewContent);

        imageView.setImageResource(R.drawable.ourmemory);
        textView1.setText("글 제목 : " + memoryDTO.getMemory_subject());
        textView2.setText("작성자 : " + memoryDTO.getMemory_name());
        textView3.setText("조회수 : " + update_hit + " / " + "추천수 : " + memoryDTO.getMemory_rec() + " / 비추천수 : " +memoryDTO.getMemory_nrec());
        textViewContent.setText(memoryDTO.getMemory_content());

        buttonBack.setOnClickListener(this);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void getJsonData() {
        RequestParams params = new RequestParams();
        params.put("memory_num", memoryDTO.getMemory_num());
        String url = "http://192.168.0.109:8081/java/viewHitJson";
        client.post(url, params, helper);
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    class ViewHelper extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {
            String str = new String(bytes);

            try {
                JSONObject json = new JSONObject(str);
                rt = json.getString("rt");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            Toast.makeText(ViewActivity.this, "실패했는데요 ㅎ?" + i + "에러가 났네요", Toast.LENGTH_SHORT).show();
        }
    }
}