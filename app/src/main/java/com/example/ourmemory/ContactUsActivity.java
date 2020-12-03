package com.example.ourmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ourmemory.fragment.NoticeFragment;
import com.example.ourmemory.fragment.OneByOneFragment;
import com.example.ourmemory.fragment.QuestionFragment;

public class ContactUsActivity extends AppCompatActivity implements View.OnClickListener {
    NoticeFragment noticeFragment;
    QuestionFragment questionFragment;
    OneByOneFragment oneByOneFragment;
    LinearLayout containerLayout;
    TextView textViewNotice, textViewOne, textViewQues;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);

        containerLayout = findViewById(R.id.containerLayout);
        textViewNotice = findViewById(R.id.textViewNotice);
        textViewOne = findViewById(R.id.textViewOne);
        textViewQues = findViewById(R.id.textViewQues);

        noticeFragment = new NoticeFragment(this);
        questionFragment = new QuestionFragment(this);
        oneByOneFragment = new OneByOneFragment(this);

        if (savedInstanceState ==null){
            getSupportFragmentManager().beginTransaction().add(R.id.containerLayout, noticeFragment).commit();
            textViewNotice.setTextColor(Color.parseColor("#FF9393"));
        }
        textViewNotice.setOnClickListener(this);
        textViewOne.setOnClickListener(this);
        textViewQues.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textViewNotice:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, noticeFragment).commit();
                textViewNotice.setTextColor(Color.parseColor("#FF9393"));
                textViewQues.setTextColor(Color.parseColor("#000000"));
                textViewOne.setTextColor(Color.parseColor("#000000"));

                break;
            case R.id.textViewOne:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, oneByOneFragment).commit();
                textViewOne.setTextColor(Color.parseColor("#FF9393"));
                textViewNotice.setTextColor(Color.parseColor("#000000"));
                textViewQues.setTextColor(Color.parseColor("#000000"));

                break;
            case R.id.textViewQues:
                getSupportFragmentManager().beginTransaction().replace(R.id.containerLayout, questionFragment).commit();
                textViewQues.setTextColor(Color.parseColor("#FF9393"));
                textViewNotice.setTextColor(Color.parseColor("#000000"));
                textViewOne.setTextColor(Color.parseColor("#000000"));

                break;
        }
    }
}