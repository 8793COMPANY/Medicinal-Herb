package com.corporation8793.medicinal_herb.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.corporation8793.medicinal_herb.R;
import com.corporation8793.medicinal_herb.activity.join.JoinActivity;
import com.corporation8793.medicinal_herb.activity.main.MainActivity2;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button join_btn = findViewById(R.id.join_btn);
        EditText id_input_box = findViewById(R.id.id_input_box);
        EditText pw_input_box = findViewById(R.id.pw_input_box);

        LinearLayout login_btn = findViewById(R.id.login_btn);


        join_btn.setOnClickListener(v->{
            Intent intent = new Intent(LoginActivity.this, JoinActivity.class);
            startActivity(intent);
        });

        login_btn.setOnClickListener(v->{
            finish();
            Intent intent = new Intent(LoginActivity.this, MainActivity2.class);
            startActivity(intent);

        });





    }


}