package com.example.ourmemory;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.ourmemory.helper.JsonLoginHelper;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

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

    Button buttonLogin, buttonJoin , buttonLoginOK, buttonToMain, buttonMasterKey, buttonMasterKey2;

    TextView textView;

    EditText editTextID, editTextPassword;

    static public Boolean LoginOK = false;
    static public Boolean isntAppJoin = false;
    static public String user_name = "";

    // 일반 로그인에 필요한 내용 구현
    JsonLoginHelper helper;
    AsyncHttpClient client;

    // 구글 로그인을 위해 작성
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient; //  만약 임포트가 안되면 sync가 제대로 되지 않은것.
//    Button google_signin;
    SignInButton google_signin;

    // 세션 함수
    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 아이디 세션을 위한 함수
        sessionManager = new SessionManager(this);

        // 일반 로그인을 위해 작성
        helper = new JsonLoginHelper(this, sessionManager);
        client = new AsyncHttpClient();

        editTextID = findViewById(R.id.editTextID);
        editTextPassword = findViewById(R.id.editTextPassword);

        // 구글 로그인을 위해 작성
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
               .requestIdToken("789653528022-hcdqulkf2trhgo50mndtum9tg96vlmet.apps.googleusercontent.com")
               .requestEmail()
               .build(); // 구글 사인인 버튼을 누를 때 기본적인 옵션 정리 코드
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mAuth = FirebaseAuth.getInstance(); // 파이어 베이스 인증 객체 초기화
        google_signin = (SignInButton) findViewById(R.id.sign_in_button);
        google_signin.setOnClickListener(this);

        // 일반 코드 시작
        login_form = findViewById(R.id.login_form);
        main_form = findViewById(R.id.main_form);

        layoutMain = findViewById(R.id.layoutMain);

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonJoin = findViewById(R.id.buttonJoin);
        buttonLoginOK = findViewById(R.id.buttonLoginOK);
        buttonToMain = findViewById(R.id.buttonToMain);
        buttonMasterKey = findViewById(R.id.buttonMasterKey);
        buttonMasterKey2 = findViewById(R.id.buttonMasterKey2);


        buttonLogin.setOnClickListener(this);
        buttonJoin.setOnClickListener(this);
        buttonLoginOK.setOnClickListener(this);
        buttonToMain.setOnClickListener(this);
        buttonMasterKey.setOnClickListener(this);
        buttonMasterKey2.setOnClickListener(this);


        lottie1 = (LottieAnimationView) findViewById(R.id.lottie1);

        textView = findViewById(R.id.textView);
        // 테스트용으로 주석하나 달아놓음.

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

    //구글 로그인 버튼 클릭이벤트도 추가해서 클릭시 아래 함수 호출.
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1); // 로그인 화면 갔다가 인증 후 다시 돌아올 때 결과값 확인용
        Toast.makeText(this, "signIn 함수 동작완료", Toast.LENGTH_SHORT).show();
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
                Intent intentJoin = new Intent(this, JoinActivity.class);
                intentJoin.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(intentJoin);
                break;
            case R.id.buttonLogin:
                login_form.setVisibility(View.VISIBLE);
                main_form.setVisibility(View.GONE);

                break;

            case R.id.buttonLoginOK:
                RequestParams params = new RequestParams();
                params.put("id", editTextID.getText().toString().trim());
                params.put("pw", editTextPassword.getText().toString().trim());
                String url = "http://192.168.1.21:8085/java/appLogin";
                client.post(url, params,  helper);

                if(LoginOK && isntAppJoin) {
                    Log.d("[Main.LoginOK]", ""+LoginOK);
                    Log.d("[Main.isntAppJoin]", ""+isntAppJoin);
                    Intent intentLogin = new Intent(this, IndexActivity.class);
                    startActivity(intentLogin);
                } else {
                    HashMap<String, String> user = sessionManager.getUserDetail();
                    String user_id = user.get(sessionManager.ID);
                    String user_name = user.get(sessionManager.NAME);
                    Intent intentAppJoin = new Intent(this, AppJoinActivity.class);
                    intentAppJoin.putExtra("user_id", user_id);
                    intentAppJoin.putExtra("user_name", user_name);
                    startActivity(intentAppJoin);
                    editTextID.setText("");
                    editTextPassword.setText("");
                }
                break;
            case R.id.buttonToMain:
                login_form.setVisibility(View.GONE);
                main_form.setVisibility(View.VISIBLE);
                sessionManager.logout();// 일단 세션 로그아웃 버튼 안 만들어서 해놨음
                break;

            case R.id.sign_in_button:
                signIn();
                break;

            case R.id.buttonMasterKey:
                String user_Id = "master";
                String user_Name = "master";
                String cate1 = "memory";
                String cate2 = "pet";
                String cate3 = "art";
                String google_Id = "3545321";
                String kakao_Id = "3231351";
                sessionManager.createSession(user_Id, user_Name, cate1,
                        cate2, cate3, google_Id, kakao_Id);
                Intent masterIntent = new Intent(this, IndexActivity.class);
                startActivity(masterIntent);
                break;
            case R.id.buttonMasterKey2:
                user_Id = "master";
                user_Name = "master";
                cate1 = "memory";
                cate2 = "pet";
                cate3 = "art";
                google_Id = "3545321";
                kakao_Id = "3231351";
                sessionManager.createSession(user_Id, user_Name, cate1,
                        cate2, cate3, google_Id, kakao_Id);
                Intent masterIntent2 = new Intent(this, Index2Activity.class);
                startActivity(masterIntent2);
                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "signIn 함수에서 나온 requestCode = " + requestCode, Toast.LENGTH_SHORT).show();
        if (requestCode == 1) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            Toast.makeText(this, "task 가져왔어 task = " + task, Toast.LENGTH_SHORT).show();
            //data는 로그인 후 가져온 데이터
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Toast.makeText(this, "account 가져왔어 account = " + account, Toast.LENGTH_SHORT).show();
                // account <= 구글로그인 정보를 담고 있음
                firebaseAuthWithGoogle(account.getIdToken()); // 로그인 결과값 출력하라는 함수
                Toast.makeText(this, "account.getIdToken() = " + account.getIdToken(), Toast.LENGTH_SHORT).show();
            } catch (ApiException e) {
                Toast.makeText(this, "account도 못가져왔어", Toast.LENGTH_SHORT).show();
                // Google Sign In failed, update UI appropriately
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() { // 로그인의 실제 성공했는지!
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { // 로그인이 성공했으면.
                            Toast.makeText(getApplicationContext(), "로그인 성공", Toast.LENGTH_SHORT).show();
                            // Sign in success, update UI with the signed-in user's information
                            // FirebaseUser = 사용자 프로필을 조작하고 인증 공급자에 연결하거나 연결을 끊고 인증 토큰을 새로 고칠 수 있음
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(getApplicationContext(), "user= " +user, Toast.LENGTH_SHORT).show();
                            updateUI(user);// 함수 호출
                        } else {                    // 로그인이 실패했으면
                            // If sign in fails, display a message to the user.
                            updateUI(null);
                        }
                    }
                });
    }

    // updateUI(user) 함수 작성 ( 실제 화면 결과화면 이동 후 추가 작성, 또는 사용자 정보 가져오게)

    private void updateUI(FirebaseUser currentUser) {
        Toast.makeText(this, "업데이트UI 함수 동작, currentUser = " + currentUser, Toast.LENGTH_SHORT).show();
        if(currentUser != null){
            Toast.makeText(this, "받아온 정보가 있어!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), GoogleLoginResultActivity.class);
            intent.putExtra("nickname", currentUser.getDisplayName()); 		// 보여지는 이름
            //url을 string 형식으로 넘겨야 에러가 안 뜸
            intent.putExtra("photoUrl", String.valueOf(currentUser.getPhotoUrl()));	// 프로필 사진 url
            intent.putExtra("email",currentUser.getEmail());			// 이메일
            intent.putExtra("phoneNumber",currentUser.getPhoneNumber());	//전화번호 전달 받기(안 받아짐)
            intent.putExtra("providerId",currentUser.getProviderId());		// firebase 전달 받기
            intent.putExtra("tenantId",currentUser.getTenantId());			// null로 옴 전달 받기
            intent.putExtra("uid",currentUser.getUid());				// 구글에서 정한 user id 인듯
            startActivity(intent);
        } else {
            Toast.makeText(this,"실패",Toast.LENGTH_SHORT).show();
        }
    }
}