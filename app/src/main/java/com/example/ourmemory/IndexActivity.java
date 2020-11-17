package com.example.ourmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 하단에 onClickListener 부분에 주석으로 해야할 내용들을 남겨놨습니다.
     * 아래쪽으로 가서 확인해주세요.
     */

    Button buttonToMemoryList;

    Button buttonSI , buttonSW, buttonSB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        buttonToMemoryList = findViewById(R.id.buttonToMemoryList);

        buttonSI = findViewById(R.id.buttonSI);
        buttonSW = findViewById(R.id.buttonSW);
        buttonSB = findViewById(R.id.buttonSB);


        buttonToMemoryList.setOnClickListener(this);
        buttonSI.setOnClickListener(this);
        buttonSW.setOnClickListener(this);
        buttonSB.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonToMemoryList:
                Intent intent = new Intent(this, ListActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                startActivity(intent);
                break;

            /** 각자 필요한 인텐트까지만해서 작업해주면돼요. 크게 코드는 이렇게 구분됩니다.
             *  Intent intent = new Intent(시작 엑티비티 , 이동할 엑티비티);
             *  startActivity(intent);
             *  일반적으로 시작 엑티비티는 this로 현재 엑티비티를 설정하면 되는거고
             *  이동할 엑티비티는 본인들이 만든 액티비티로 이동시키면 됩니다.
             *
             *  위의 코드를 확인하면 buttonToMemoryList 버튼에 대한 이벤트 처리가 되어있는데,
             *  해당 코드를 참고해서 아래 case를 자기꺼에 맞게 처리하시면 됩니다.
             */

            case R.id.buttonSI:
                // 성인누나가 새롭게 Activity 만들어서 여기서 이동시키면돼.
                break;
            case R.id.buttonSW:
                // 승원이도 하나 Activity 만들어서 이동시켜주자.
                break;
            case R.id.buttonSB:
                // 세번쓰기 힘들다 성빈아 만들어서 이동시켜주자.
                break;
        }
    }
}