package com.example.ourmemory;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 하단에 onClickListener 부분에 주석으로 해야할 내용들을 남겨놨습니다.
     * 아래쪽으로 가서 확인해주세요.
     */

    Button buttonPet;

    Button buttonFood , buttonHealth, buttonGame, buttonTotal;
    Button buttonTravel, buttonMusic, buttonArt, buttonIt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        buttonPet = findViewById(R.id.buttonPet);
        buttonFood = findViewById(R.id.buttonFood);
        buttonHealth = findViewById(R.id.buttonHealth);
        buttonGame = findViewById(R.id.buttonGame);
        buttonTravel = findViewById(R.id.buttonTravel);
        buttonMusic = findViewById(R.id.buttonMusic);
        buttonArt = findViewById(R.id.buttonArt);
        buttonIt = findViewById(R.id.buttonIt);
        buttonTotal = findViewById(R.id.buttonTotal);

        buttonPet.setOnClickListener(this);
        buttonFood.setOnClickListener(this);
        buttonHealth.setOnClickListener(this);
        buttonGame.setOnClickListener(this);
        buttonTravel.setOnClickListener(this);
        buttonMusic.setOnClickListener(this);
        buttonArt.setOnClickListener(this);
        buttonIt.setOnClickListener(this);
        buttonTotal.setOnClickListener(this);
    }
    /** 각자 필요한 인텐트까지만해서 작업해주면돼요. 크게 코드는 이렇게 구분됩니다.
     *  Intent intent = new Intent(시작 엑티비티 , 이동할 엑티비티);
     *  startActivity(intent);
     *  일반적으로 시작 엑티비티는 this로 현재 엑티비티를 설정하면 되는거고
     *  이동할 엑티비티는 본인들이 만든 액티비티로 이동시키면 됩니다.
     *
     *  위의 코드를 확인하면 buttonToMemoryList 버튼에 대한 이벤트 처리가 되어있는데,
     *  해당 코드를 참고해서 아래 case를 자기꺼에 맞게 처리하시면 됩니다.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonTravel:
                Intent intent = new Intent(this, ListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent);
                break;
            case R.id.buttonPet:
                // 반려동물!
                Intent intentPet = new Intent(this, PetListActivity.class);
                intentPet.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intentPet);
                break;
            case R.id.buttonFood:
                // 성인누나가 새롭게 Activity 만들어서 여기서 이동시키면돼.
                Intent intentSIFood = new Intent(this, FoodListActivity.class);
                intentSIFood.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intentSIFood);
                break;
            case R.id.buttonHealth:
                // 승원이도 하나 Activity 만들어서 이동시켜주자.
                Intent intentSW = new Intent(this, HealthListActivity.class);
                intentSW.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intentSW);
                break;
            case R.id.buttonGame:
                // 세번쓰기 힘들다 성빈아 만들어서 이동시켜주자.
                Intent intentGame = new Intent(this, GameListActivity.class);
                intentGame.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intentGame);
                break;
            case R.id.buttonMusic:
                // 음악 category.
                Intent intentMusic = new Intent(this, MusicListActivity.class);
                intentMusic.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intentMusic);
                break;
            case R.id.buttonIt:
                // IT category.
                Intent intentIt = new Intent(this, ItListActivity.class);
                intentIt.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intentIt);
                break;
            case R.id.buttonTotal:
                // 선택한 카테고리 모아보기
                Intent intentTotal = new Intent(this, TotalListActivity.class);
                intentTotal.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intentTotal);
                break;

        }
    }
}