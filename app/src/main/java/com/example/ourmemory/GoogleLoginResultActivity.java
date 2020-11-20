package com.example.ourmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class GoogleLoginResultActivity extends AppCompatActivity {

    TextView result;
    ImageView profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String nickname = intent.getStringExtra("nickname");        // 닉네임 전달 받기
        String email = intent.getStringExtra("email");              // 이메일 전달 받기
        String phoneNumber = intent.getStringExtra("phoneNumber");  // 전화번호 전달 받기(안 받아짐)
        String providerId = intent.getStringExtra("providerId");    // firebase 전달 받기
        String tenantId = intent.getStringExtra("tenantId");        // null로 옴 전달 받기
        String photoUrl = intent.getStringExtra("photoUrl");        // 사진 전달 받기
        String uid = intent.getStringExtra("uid");        // 사진 전달 받기

        result = findViewById(R.id.resultString);
        profile = findViewById(R.id.resultProfile);

        result.setText("이름 : " +nickname + "\nuid : "+ uid
                +"\nemail : "+email+"\nphoneNumber : "+phoneNumber+
                "\nproviderId : "+providerId +"\ntenantId : "+tenantId
        );
        Glide.with(this).load(photoUrl).into(profile); // 프로필 url을 세팅
    }
}