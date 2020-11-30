package com.example.ourmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ourmemory.R;

public class EventActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);

        buttonBack = findViewById(R.id.buttonBack);

        buttonBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}