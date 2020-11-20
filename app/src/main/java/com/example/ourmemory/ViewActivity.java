package com.example.ourmemory;

import androidx.appcompat.app.AppCompatActivity;
import cz.msebera.android.httpclient.Header;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ourmemory.adapter.MemoryAdapter;
import com.example.ourmemory.adapter.MemoryCommentAdapter;
import com.example.ourmemory.helper.JsonCommentHelper;
import com.example.ourmemory.helper.JsonHelper;
import com.example.ourmemory.model.MemoryCommentDTO;
import com.example.ourmemory.model.MemoryDTO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener {

    // 서버 연동을 하기 위해 필요한 코드
    ViewHelper helper;
    RecommandHelper recommandHelper;
    JsonCommentHelper commentHelper;
    MemoryCommentAdapter commentAdapter;
    AsyncHttpClient client;
    String rt = null;

    ListView listView;
    List<MemoryCommentDTO> list;

    MemoryDTO memoryDTO;
    ImageView imageView;
    TextView textView1, textView2, textView3, textViewContent, textView9, textView10;
    Button buttonBack, buttonCommentSubmit;
    EditText editTextCommentContent, editTextCommentName;
    boolean statusLike = false;
    int like_status = 0;

    // 확인용 주석
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        buttonCommentSubmit = findViewById(R.id.buttonCommentSubmit);
        editTextCommentContent = findViewById(R.id.editTextCommentContent);
        editTextCommentName = findViewById(R.id.editTextCommentName);

        helper = new ViewHelper();
        recommandHelper = new RecommandHelper();
        client = new AsyncHttpClient();
        listView = findViewById(R.id.listView);
        list = new ArrayList<>();

        commentAdapter = new MemoryCommentAdapter(this, R.layout.comment_list_item, list);
        commentHelper = new JsonCommentHelper(this, commentAdapter, listView);

        memoryDTO = (MemoryDTO) getIntent().getSerializableExtra("dto");
        getJsonData(); // 제이슨 데이터 처리!


        memoryDTO = (MemoryDTO) getIntent().getSerializableExtra("dto");

        String full_filename = "http://192.168.1.21:8085/java/img" + "/" + memoryDTO.getMemory_file();

        // 1 증가한 조회수를 미리 받아버리기~
        int update_hit = getIntent().getIntExtra("memory_hit", 0);

        buttonBack = findViewById(R.id.buttonBack);
        imageView = findViewById(R.id.imageView);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView9 = findViewById(R.id.textView9);
        textView10 = findViewById(R.id.textView10);
        textViewContent = findViewById(R.id.textViewContent);

        listView.setAdapter(commentAdapter);
        getCommentData();

        Glide.with(this).load(full_filename)
                .into(imageView);
        textView1.setText("글 제목 : " + memoryDTO.getMemory_subject());
        textView2.setText("작성자 : " + memoryDTO.getMemory_name());
        textView3.setText("조회수 : " + update_hit);
        textView9.setText("추천수 : " + memoryDTO.getMemory_rec());
        textView10.setText("비추천수 : " +memoryDTO.getMemory_nrec());
        textViewContent.setText(memoryDTO.getMemory_content());

        buttonBack.setOnClickListener(this);
        textView9.setOnClickListener(this);
        textView10.setOnClickListener(this);
        buttonCommentSubmit.setOnClickListener(this);
    }

    // 댓글을 입력한 이후에, 화면이 초기화되는 작업이 필요하다. 이 부분은 resume으로 해야하는지 알아볼 것.
    @Override
    protected void onResume() {
        super.onResume();
        getCommentData();
    }

    private void getJsonData() {
        RequestParams params = new RequestParams();
        params.put("memory_num", memoryDTO.getMemory_num());
        String url = "http://192.168.1.21:8085/java/viewHitJson";
        client.post(url, params, helper);
    }

    private void getCommentData() {
        RequestParams params = new RequestParams();
        params.put("seq", memoryDTO.getMemory_num());
        String url = "http://192.168.1.21:8085/java/commentViewJson";
        client.post(url, params,  commentHelper);
    }

    private void CommentWriteJson() {
        RequestParams params = new RequestParams();
        params.put("memory_seq", memoryDTO.getMemory_num());
        params.put("memory_comment_name", editTextCommentName.getText().toString().trim());
        params.put("memory_comment_content", editTextCommentContent.getText().toString().trim());
        String url = "http://192.168.1.21:8085/java/viewCommentWriteJson";
        client.post(url, params,  commentHelper);
        onResume();

        editTextCommentName.setText("");
        editTextCommentContent.setText("");
    }

    @Override
    protected void onPause() {
        super.onPause();
        recommandData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonBack:
                finish();
                break;
            case R.id.textView9:
                if (!statusLike){
                    textView9.setText("추천수 : " + (memoryDTO.getMemory_rec()+1));
                    like_status = 1; statusLike = true;
                    Toast.makeText(this,"추천하셨습니다.",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,"이미 추천 / 비추천하셨습니다.",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.textView10:
                if (!statusLike){
                    textView10.setText("비추천수 : " +(memoryDTO.getMemory_nrec()+1));
                    like_status = 2; statusLike = true;
                    Toast.makeText(this,"비추천하셨습니다.",Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this,"이미 추천 / 비추천 하셨습니다.",Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.buttonCommentSubmit:
                // 댓글 작성 후 저장하는 버튼
                if(editTextCommentName.getText() == null || editTextCommentName.getText().toString().equals("")) {
                    Toast.makeText(this,"댓글에 쓸 이름을 작성해주세요.",Toast.LENGTH_SHORT).show();
                } else if(editTextCommentContent.getText() == null || editTextCommentContent.getText().toString().equals("")) {
                    Toast.makeText(this,"댓글을 작성해주세요.",Toast.LENGTH_SHORT).show();
                } else {
                    CommentWriteJson();
                }
                break;
        }

    }

    private void recommandData() {
        RequestParams params = new RequestParams();
        if (like_status == 1){
            String url = "http://192.168.1.21:8085/java/recommendation";
            params.put("memory_num", memoryDTO.getMemory_num());
            client.post(url, params, recommandHelper);
            Log.d("[test]",like_status+" ");
        } else if (like_status == 2){
            params.put("memory_num", memoryDTO.getMemory_num());
            String url = "http://192.168.1.21:8085/java/notrecommendation";
            client.post(url, params, recommandHelper);
            Log.d("[test]",like_status+" ");
        }
        like_status = 0;
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

    class RecommandHelper extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {
            Toast.makeText(ViewActivity.this, "추천 / 비추천 성공했습니다. ", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            Toast.makeText(ViewActivity.this, "실패했는데요 ㅎ?" + i + "에러가 났네요", Toast.LENGTH_SHORT).show();
        }
    }
}