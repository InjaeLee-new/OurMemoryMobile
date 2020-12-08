package com.example.ourmemory;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.ourmemory.adapter.MemoryAdapter;
import com.example.ourmemory.helper.JsonHelper;
import com.example.ourmemory.model.MemoryDTO;
import com.loopj.android.http.AsyncHttpClient;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ListActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {

        JsonHelper helper;
        AsyncHttpClient client;
        MemoryAdapter adapter;
        ListView listView;
        List<MemoryDTO> list;

        // 상단 툴바
        Toolbar toolbar;
        ImageButton toolBack;
        // 하단 메뉴_푸터
        ImageButton btnHome, btnWrite, btnFav, btnMypage;
        // 화면이동 전역변수 인텐트
        Intent intent;

        // 세션관리
        SessionManager sessionManager;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_list);

                listView = findViewById(R.id.listView);


                list = new ArrayList<>();
                adapter = new MemoryAdapter(this, R.layout.list_item, list);

                client = new AsyncHttpClient();
                helper = new JsonHelper(this, adapter, listView);

                listView.setAdapter(adapter);
                getJsonData();

                listView.setOnItemClickListener(this);

                // 세션관리
                sessionManager = new SessionManager(this);

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

        }

        @Override
        protected void onResume() {
                super.onResume();
                // 주석 추가한 상태로 다시 저장하자.
                adapter.clear();
                getJsonData();
        }

        private void getJsonData() {
//                String url = "http://192.168.1.3:8085/java/listJson";
                String url = "http://192.168.0.9:8085/java/listJson";
                client.get(url, helper);
        }

        @Override
        public void onClick(View v) {
                switch (v.getId()) {
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
                }
        }

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MemoryDTO dto = list.get(position);

                Intent intent = new Intent(this, ViewActivity.class);
                intent.putExtra("dto", dto);

                // View에서 hit 수가 1 증가하는 부분은 다시 리스트로 돌아올때 적용된다.
                // 그래서 리스트에서 view로 넘어갈때 임의로 조회수를 1 증가시켜서 보여주기되면 바로바로 실시간 적용이 가능하다.
                // by 승원
                intent.putExtra("memory_hit", dto.getMemory_hit()+1);
                startActivity(intent);
        }//

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

                        case R.id.action_logout:
                                sessionManager.logout();
                                Intent intent10 = new Intent(this, MainActivity.class);
                                intent10.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent10);
                                MainActivity.LoginOK =false;
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
}