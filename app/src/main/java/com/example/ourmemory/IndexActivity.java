package com.example.ourmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class IndexActivity extends AppCompatActivity implements View.OnClickListener {

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