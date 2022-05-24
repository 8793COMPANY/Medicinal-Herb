package com.corporation8793.medicinal_herb.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.corporation8793.medicinal_herb.R;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        ConstraintLayout background = findViewById(R.id.intro_background);
        Button go_login_page_btn = findViewById(R.id.go_login_page_btn);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable(){
            @Override
            public void run() {
             background.setBackgroundResource(R.drawable.intro2);
             go_login_page_btn.setVisibility(View.VISIBLE);
            }
        },2500); //1초 후 인트로 실행

        go_login_page_btn.setOnClickListener(v->{
                    Intent intent = new Intent (getApplicationContext(), LoginActivity.class);
        startActivity(intent); //인트로 실행 후 바로 MainActivity로 넘어감.
        finish();
        });


    }
}