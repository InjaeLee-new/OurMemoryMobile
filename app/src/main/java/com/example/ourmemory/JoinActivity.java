package com.example.ourmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonJoin, buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        buttonJoin = findViewById(R.id.buttonJoin);
        buttonBack = findViewById(R.id.buttonBack);

        buttonJoin.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonJoin:
                break;
            case R.id.buttonBack:
                finish();
                break;
        }
    }
}