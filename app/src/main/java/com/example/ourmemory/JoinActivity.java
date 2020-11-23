package com.example.ourmemory;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonJoin, buttonBack;

    ImageButton imageButton;

    CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8;

    EditText editTextName, editTextId, editTextPwd, editTextNick, editTextTel1, editTextTel2, editTextTel3,
            editTextEmail1, editTextEmail2;

    RadioGroup radioGender;
    RadioButton radioBtnM, radioBtnF;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        buttonJoin = findViewById(R.id.buttonJoin);
        buttonBack = findViewById(R.id.buttonBack);

        imageButton = findViewById(R.id.imageButton);
        buttonJoin = findViewById(R.id.buttonJoin);
        buttonBack = findViewById(R.id.buttonBack);

        checkBox1 = findViewById(R.id.checkBox1);
        checkBox2 = findViewById(R.id.checkBox2);
        checkBox3 = findViewById(R.id.checkBox3);
        checkBox4 = findViewById(R.id.checkBox4);
        checkBox5 = findViewById(R.id.checkBox5);
        checkBox6 = findViewById(R.id.checkBox6);
        checkBox7 = findViewById(R.id.checkBox7);
        checkBox8 = findViewById(R.id.checkBox8);

        editTextName = findViewById(R.id.editTextName);
        editTextId = findViewById(R.id.editTextID);
        editTextPwd = findViewById(R.id.editTextPwd);
        editTextNick = findViewById(R.id.editTextNick);
        editTextEmail1 = findViewById(R.id.editTextEmail1);
        editTextEmail2 = findViewById(R.id.editTextEmail2);

        editTextTel1 = findViewById(R.id.editTextTel1);
        editTextTel2 = findViewById(R.id.editTextTel2);
        editTextTel3 = findViewById(R.id.editTextTel3);

        radioGender = findViewById(R.id.radioGender);
        radioBtnM = findViewById(R.id.radioBtnM);
        radioBtnF = findViewById(R.id.radioBtnF);

        buttonJoin.setOnClickListener(this);
        buttonBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonJoin:
                MemoryJoinAction();
                break;
            case R.id.buttonBack:
                finish();
                break;
        }
    }

    public void MemoryJoinAction() {

    }
}