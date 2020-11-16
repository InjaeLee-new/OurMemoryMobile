package com.example.ourmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout layoutMain, layoutSecond, login_form, main_form;
    LottieAnimationView lottie1, lottie2, lottie3;

    Button buttonLogin, buttonJoin , buttonLoginOK, buttonToMain;

    TextView textView;

    Boolean LoginOK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        login_form = findViewById(R.id.login_form);
        main_form = findViewById(R.id.main_form);

        layoutMain = findViewById(R.id.layoutMain);

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonJoin = findViewById(R.id.buttonJoin);
        buttonLoginOK = findViewById(R.id.buttonLoginOK);
        buttonToMain = findViewById(R.id.buttonToMain);


        buttonLogin.setOnClickListener(this);
        buttonJoin.setOnClickListener(this);
        buttonLoginOK.setOnClickListener(this);
        buttonToMain.setOnClickListener(this);

//        lottie = (LottieAnimationView) findViewById(R.id.lottie);
//        lottie2 = (LottieAnimationView) findViewById(R.id.lottie2);
        lottie1 = (LottieAnimationView) findViewById(R.id.lottie1);

        textView = findViewById(R.id.textView);

//        lottie.playAnimation();

//        lottie.loop(true);
//        lottie.getDuration();

//         lottie 내장 함수로 Listener를 다 쓸 필요가 없으니 Adapter로 구현한다.
//        lottie.addAnimatorListener(new AnimatorListenerAdapter() {
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                lottie.setVisibility(View.GONE);
//                lottie2.setVisibility(View.VISIBLE);
//
//                lottie2.playAnimation();
//            }
//        });

//        lottie2.addAnimatorListener(new AnimatorListenerAdapter() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                super.onAnimationEnd(animation);
//                layoutChange = true;
//
//                if(layoutChange) {
        layoutMain.setVisibility(View.VISIBLE);
//      layoutSecond.setVisibility(View.VISIBLE);
        login_form.setVisibility(View.GONE);
        main_form.setVisibility(View.VISIBLE);

        lottie1.setVisibility(View.VISIBLE);

        lottie1.playAnimation();
        lottie1.loop(true);


    }

    @Override
    public void onClick(View v) {
        //에니메이션 로드
//        Animation translate = AnimationUtils.loadAnimation(MainActivity.this, R.anim.translate);
//        Animation buttonshowup = AnimationUtils.loadAnimation(MainActivity.this, R.anim.buttonshowup);
//        Animation buttonfadeoff = AnimationUtils.loadAnimation(MainActivity.this, R.anim.buttonfadeout);
        //에니메이션 동작
//        textView.startAnimation(translate);
//        buttonLogin.startAnimation(buttonfadeoff);
//        buttonJoin.startAnimation(buttonfadeoff);
        switch (v.getId()) {
            case R.id.buttonJoin:
                break;
            case R.id.buttonLogin:
                login_form.setVisibility(View.VISIBLE);
                main_form.setVisibility(View.GONE);
                break;

            case R.id.buttonLoginOK:
                LoginOK = true;
                if(LoginOK == true) {
                    Intent intent = new Intent(this, IndexActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.buttonToMain:
                login_form.setVisibility(View.GONE);
                main_form.setVisibility(View.VISIBLE);
                break;

        }
    }
}