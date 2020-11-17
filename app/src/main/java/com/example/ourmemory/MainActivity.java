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

    /**
     * 메인 엑티비티에서 다른 버튼들은 아직 미구현.
     * 메인에 진입했을 때 로그인하기 버튼을 눌러서
     * 로그인 창이 뜨면 암거나 입력해도 상관없습니다.
     *
     * 1. 메인 엑티비티에서 로그인 버튼을 클릭하면 현재 메인페이지에 로티 파일은 지워지고
     *    로그인 창이 동작합니다.
     * 2. 로그인 기능을 미리 구현하면, 여러분들이 받고나서 데이터 처리가 애매해 일부러 처리 안했습니다.
     *
     * 3. 아무거나 맞춰서 로그인하면 제가 작업하라고 만들어 놓은 파일이 있습니다.
     *
     * 4. 현재 리스트버튼을 누르면 서버 연동이 안되서 아마 여러분들 환경에선 실행이 안될겁니다.
     *
     * 5. 대신 각자 한명씩 버튼을 만들어 놨는데. 자세한 내용은 IndexActivity에서 확인하시면 됩니다.
     */

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

//        lottie1 = (LottieAnimationView) findViewById(R.id.lottie1);

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