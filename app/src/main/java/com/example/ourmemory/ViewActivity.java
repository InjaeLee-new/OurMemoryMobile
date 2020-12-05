package com.example.ourmemory;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import cz.msebera.android.httpclient.Header;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.ourmemory.adapter.MemoryCommentAdapter;
import com.example.ourmemory.helper.JsonCommentHelper;

import com.example.ourmemory.helper.ViewPagerHelper;
import com.example.ourmemory.model.MemoryCommentDTO;
import com.example.ourmemory.model.MemoryDTO;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener {

    // 서버 연동을 하기 위해 필요한 코드
    ViewHelper helper;
    RecommandHelper recommandHelper;
    JsonCommentHelper commentHelper;
    RecommandCheckHelper recommandCheckHelper;
    MemoryCommentAdapter commentAdapter;
    AsyncHttpClient client;
    String rt = null;
    String rt2 = ""; // 추천 확인용 result

    ListView listView;
    List<MemoryCommentDTO> list;

    MemoryDTO memoryDTO;
    ImageView imageView;
    TextView textViewName, textView1, textView2, textView3, textView4, textView5,textViewContent;

    Button buttonBack, buttonCommentSubmit, buttonShare, buttonModify, buttonDelete;
    ImageButton imageButtonPre, imageButtonNext;
    ImageButton imageButtonLike, imageButtonDis, imageButtonComm;
    ViewPager2 viewPager;

    EditText editTextCommentContent, editTextCommentName;
    boolean statusLike = false;

    // 세션 사용을 위해 세션 매니저 선언
    SessionManager sessionManager;
    String session_id;

    // 상단 툴바
    Toolbar toolbar;
    ImageButton toolBack;
    // 하단 메뉴_푸터
    ImageButton btnHome, btnWrite, btnFav, btnMypage;
    // 화면이동 전역변수 인텐트
    Intent intent;

    // 댓글창으로 이동하기 위한 스크롤 뷰
    ScrollView scrollView;

    int re_check= 0;

    // 확인용 주석
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        sessionManager = new SessionManager(this);
        HashMap<String, String> user = sessionManager.getUserDetail();
        session_id = user.get(sessionManager.ID);

        // 툴바관리
        toolbar = findViewById(R.id.toolbar);
        toolBack = findViewById(R.id.toolBack);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolBack.setOnClickListener(this);

        // 하단 메뉴_푸터
        btnHome = findViewById(R.id.btnHome);
        btnWrite = findViewById(R.id.btnWrite);
        btnFav = findViewById(R.id.btnFav);
        btnMypage = findViewById(R.id.btnMypage);
        btnHome.setOnClickListener(this);
        btnWrite.setOnClickListener(this);
        btnFav.setOnClickListener(this);
        btnMypage.setOnClickListener(this);

        buttonCommentSubmit = findViewById(R.id.buttonCommentSubmit);
        editTextCommentContent = findViewById(R.id.editTextCommentContent);
        editTextCommentName = findViewById(R.id.editTextCommentName);
        buttonShare = findViewById(R.id.buttonShare);
        buttonModify = findViewById(R.id.buttonModify);
        buttonDelete = findViewById(R.id.buttonDelete);

        imageButtonPre = findViewById(R.id.imageButtonPre);
        imageButtonNext = findViewById(R.id.imageButtonNext);
        imageButtonLike = findViewById(R.id.imageButtonLike);
        imageButtonDis = findViewById(R.id.imageButtonDis);
        imageButtonComm = findViewById(R.id.imageButtonComm);

        viewPager =  findViewById(R.id.viewPager);

        helper = new ViewHelper();
        recommandHelper = new RecommandHelper();
        recommandCheckHelper = new RecommandCheckHelper();
        client = new AsyncHttpClient();
        listView = findViewById(R.id.listView);
        list = new ArrayList<>();

        commentAdapter = new MemoryCommentAdapter(this, R.layout.comment_list_item, list);
        commentHelper = new JsonCommentHelper(this, commentAdapter, listView);

        memoryDTO = (MemoryDTO) getIntent().getSerializableExtra("dto");

        getJsonData(); // 제이슨 데이터 처리!

        String fileName = memoryDTO.getMemory_file();
        String[] array_fileName = fileName.split(", ");
        String full_filename = "http://192.168.0.42:8088/java/img" + "/" + array_fileName[0];

        // viewpager 만들기
        viewPager.setAdapter(new ViewPagerHelper(array_fileName, this));

        // 1 증가한 조회수를 미리 받아버리기~
        int update_hit = getIntent().getIntExtra("memory_hit", 0);

//        buttonBack = findViewById(R.id.buttonBack);
//        buttonModify = findViewById(R.id.buttonModify);
//        buttonDelete = findViewById(R.id.buttonDelete);
//        imageView = findViewById(R.id.imageView);

        textViewName = findViewById(R.id.textViewName);
        textView1 = findViewById(R.id.textView1);
        textView2 = findViewById(R.id.textView2);
        textView3 = findViewById(R.id.textView3);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
//        textView9 = findViewById(R.id.textView9);
//        textView10 = findViewById(R.id.textView10);
        textViewContent = findViewById(R.id.textViewContent);

        listView.setAdapter(commentAdapter);
        //getCommentData();

//        Glide.with(this).load(full_filename)
//                .into(imageView);
        textViewName.setText(memoryDTO.getMemory_name());
        textView1.setText("\uD83D\uDE0D"+memoryDTO.getMemory_rec()+"명이 좋아합니다.");
        textView2.setText("\uD83D\uDE2B"+memoryDTO.getMemory_nrec()+"명이 싫어합니다.");
        textView3.setText(update_hit+"명이 조회했습니다.");
        textView4.setText(memoryDTO.getMemory_name());
        textView5.setText(memoryDTO.getMemory_subject());

//        textView9.setText("추천수 : " + memoryDTO.getMemory_rec());
//        textView10.setText("비추천수 : " +memoryDTO.getMemory_nrec());
        textViewContent.setText(memoryDTO.getMemory_content());

//        buttonBack.setOnClickListener(this);
        buttonShare.setOnClickListener(this);
        imageButtonPre.setOnClickListener(this);
        imageButtonNext.setOnClickListener(this);
        buttonModify.setOnClickListener(this);
        buttonDelete.setOnClickListener(this);

        imageButtonLike.setOnClickListener(this);
        imageButtonDis.setOnClickListener(this);
        imageButtonComm.setOnClickListener(this);
//        textView9.setOnClickListener(this);   => imageButtonLike로 대체
//        textView10.setOnClickListener(this);  => imageButtonDis로 대체
        buttonCommentSubmit.setOnClickListener(this);

        scrollView = findViewById(R.id.scrollView);

    }

    // 댓글을 입력한 이후에, 화면이 초기화되는 작업이 필요하다. 이 부분은 resume으로 해야하는지 알아볼 것.
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("[TEST adapter]", ""+commentAdapter.getCount());
        commentAdapter.clear();
        getCommentData();
    }

    private void getJsonData() {
        RequestParams params = new RequestParams();
        params.put("memory_num", memoryDTO.getMemory_num());
        String url = "http://192.168.0.42:8088/java/viewHitJson";

        client.post(url, params, helper);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_actions, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
//            case R.id.action_contactus:    //세팅 액티비티로 가도록 이동
//                Intent intent = new Intent(this, SettingsActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                startActivity(intent);
//                break;
            case R.id.action_logout:
                // 로그아웃 테스트
                // 이후 로그아웃 버튼 생성시 sessionManager.logout(); 함수 실행
                sessionManager.logout();
                finish();
                MainActivity.LoginOK =false;
                break;
            case R.id.memory :
                Intent intent1 = new Intent(this, ListActivity.class);
                intent1.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent1);
                break;
            case R.id.pet :
                Intent intent2 = new Intent(this, PetListActivity.class);
                intent2.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent2);
                break;
            case R.id.it :
                Intent intent3 = new Intent(this, ItListActivity.class);
                intent3.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent3);
                break;
            case R.id.game :
                Intent intent4 = new Intent(this, GameListActivity.class);
                intent4.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent4);
                break;
            case R.id.food :
                Intent intent5 = new Intent(this, FoodListActivity.class);
                intent5.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent5);
                break;
            case R.id.music :
                Intent intent6 = new Intent(this, MusicListActivity.class);
                intent6.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent6);
                break;
            case R.id.art :
                Intent intent7 = new Intent(this, ArtListActivity.class);
                intent7.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent7);
                break;
            case R.id.health :
                Intent intent8 = new Intent(this, HealthListActivity.class);
                intent8.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent8);
                break;
            case R.id.action_contactus: // 고객센터 관련 activity로 이동
                Intent intent9 = new Intent(this, ContactUsActivity.class);
                intent9.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent9);
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    private void getCommentData() {
        RequestParams params = new RequestParams();
        params.put("seq", memoryDTO.getMemory_num());
        String url = "http://192.168.0.42:8088/java/commentViewJson";

        client.post(url, params,  commentHelper);
    }

    private void CommentWriteJson() {
        RequestParams params = new RequestParams();
        params.put("memory_seq", memoryDTO.getMemory_num());
        params.put("memory_comment_name", editTextCommentName.getText().toString().trim());
        params.put("memory_comment_content", editTextCommentContent.getText().toString().trim());
        String url = "http://192.168.0.42:8088/java/viewCommentWriteJson";
        client.post(url, params,  commentHelper);

        editTextCommentName.setText("");
        editTextCommentContent.setText("");
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onClick(View v) {
        int position;
        switch (v.getId()){
            case R.id.btnHome:      // 홈화면 (모든 리스트 보이는 현재 화면)
                intent = new Intent(this, Index2Activity.class);
                startActivity(intent);
                break;
            case R.id.btnWrite:     // 작성화면 (WriteActivity)
                intent = new Intent(this, WriteActivity.class);
                startActivity(intent);
                break;
            case R.id.btnFav:       // 좋아요/비추천 누른 게시물만
                intent = new Intent(this, FavoriteActivity.class);
                startActivity(intent);
                break;
            case R.id.btnMypage:     // 마이페이지
                intent = new Intent(this, MypageActivity.class);
                startActivity(intent);
                break;
            case R.id.toolBack :
                finish();
                break;
            case R.id.buttonBack:
                finish();
                break;
            case R.id.imageButtonLike:
                // 추천 버튼
                re_check = 1;
                recommandCheck(); // 1계정당 1게시물 추천 가능 함수
                break;
            case R.id.imageButtonDis:
                // 비추 버튼
                re_check = 2;
                recommandCheck(); // 1계정당 1게시물 추천 가능 함수
                break;

            case R.id.buttonCommentSubmit:
                // 댓글 작성 후 저장하는 버튼
                if(editTextCommentName.getText() == null || editTextCommentName.getText().toString().equals("")) {
                    Toast.makeText(this,"댓글에 쓸 이름을 작성해주세요.",Toast.LENGTH_SHORT).show();
                } else if(editTextCommentContent.getText() == null || editTextCommentContent.getText().toString().equals("")) {
                    Toast.makeText(this,"댓글을 작성해주세요.",Toast.LENGTH_SHORT).show();
                } else {
                    CommentWriteJson();
                    finish();
                    Intent reStartIntent = new Intent(this, ViewActivity.class);
                    reStartIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    reStartIntent.putExtra("dto", memoryDTO);
                    startActivity(reStartIntent);
                    Toast.makeText(this, "댓글 작성이 완료되었습니다.", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.buttonShare: // 공유하기 버튼
                Intent intent = new Intent(android.content.Intent.ACTION_SEND);
                intent.setType("text/plain");

                // Set default text message
                // 카톡, 이메일, MMS 다 이걸로 설정 가능
                String subject = memoryDTO.getMemory_subject(); // url 앞에 들어가는 문구
                // URL 보내는 곳 ( 임시로 플레이스토어 이동 인스타 다운로드로 경로 지정해둠 )
                String text = "https://play.google.com/store/apps/details?id=com.instagram.android";
                intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                intent.putExtra(Intent.EXTRA_TEXT, text);

                // Title of intent
                Intent chooser = Intent.createChooser(intent, "친구에게 공유하기");
                startActivity(chooser);
                break;
            case R.id.buttonModify:
                Intent intentModify = new Intent(this, ModifyActivity.class);
                intentModify.putExtra("dto", memoryDTO);
                startActivity(intentModify);
                break;
            case R.id.buttonDelete:
                Intent intentDelete = new Intent(this, DeleteActivity.class);
                intentDelete.putExtra("dto", memoryDTO);
                startActivity(intentDelete);
                break;

            case R.id.imageButtonPre:
                position = viewPager.getCurrentItem();//현재 보여지는 아이템의 위치를 리턴

                viewPager.setCurrentItem(position-1,true);
                break;
            case R.id.imageButtonNext:
                position = viewPager.getCurrentItem();//현재 보여지는 아이템의 위치를 리턴

                viewPager.setCurrentItem(position+1,true);
                break;

            case R.id.imageButtonComm :
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);}
                });
                break;
        }

    }

    private void recommandCheck() {
        RequestParams params = new RequestParams();

        String url = "http://192.168.0.42:8088/java/recommandCheck";
        params.put("recommand_id", session_id);
        params.put("recommand_seq", memoryDTO.getMemory_num());
        client.post(url, params, recommandCheckHelper);

    }

    private void recommandData() {
        RequestParams params = new RequestParams();
        if (re_check == 1){
            String url = "http://192.168.0.42:8088/java/recommendation";
            params.put("memory_num", memoryDTO.getMemory_num());
            client.post(url, params, recommandHelper);
        } else if (re_check == 2){
            params.put("memory_num", memoryDTO.getMemory_num());
            String url = "http://192.168.0.42:8088/java/notrecommendation";
            client.post(url, params, recommandHelper);
        }
        re_check = 0;
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
//            Toast.makeText(ViewActivity.this, "추천 / 비추천 성공했습니다. ", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            Toast.makeText(ViewActivity.this, "추 / 비추 실패했는데요 ㅎ?" + i + "에러가 났네요", Toast.LENGTH_SHORT).show();
        }
    }
    class RecommandCheckHelper extends AsyncHttpResponseHandler {

        @Override
        public void onSuccess(int i, Header[] headers, byte[] bytes) {
            String str = new String(bytes);

            try {
                JSONObject json = new JSONObject(str);
                rt2 = json.getString("rt");

                if (rt2.equals("OK")&&re_check==1){
                    if (!statusLike){
                        textView1.setText("\uD83D\uDE0D"+(memoryDTO.getMemory_rec()+1)+"명이 좋아합니다.");
                        //textView9.setText("추천수 : " + (memoryDTO.getMemory_rec()+1));
                        statusLike = true;
                        recommandData();
                        Toast.makeText(ViewActivity.this,"추천하셨습니다.",Toast.LENGTH_SHORT).show();
                    }
                } else if (rt2.equals("Exist")&&re_check==1) {
                    Toast.makeText(ViewActivity.this,"이미 추천 / 비추천하셨습니다.",Toast.LENGTH_SHORT).show();
                }

                if (rt2.equals("OK")&&re_check==2){
                    if (!statusLike){
                        //textView10.setText("비추천수 : " +(memoryDTO.getMemory_nrec()+1));
                        textView2.setText("\uD83D\uDE2B"+(memoryDTO.getMemory_nrec()+1)+"명이 싫어합니다.");
                        statusLike = true;
                        recommandData();
                        Toast.makeText(ViewActivity.this,"비추천하셨습니다.",Toast.LENGTH_SHORT).show();
                    }
                } else if (rt2.equals("Exist")&&re_check==2){
                    Toast.makeText(ViewActivity.this,"이미 추천 / 비추천 하셨습니다.",Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Toast.makeText(ViewActivity.this, "추천 / 비추천 Check 성공. ", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
            Toast.makeText(ViewActivity.this, "Check " + i + "에러가 났네요", Toast.LENGTH_SHORT).show();
        }
    }
}