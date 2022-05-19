package com.corporation8793.medicinal_herb.activity.join;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import com.corporation8793.medicinal_herb.R;
import com.corporation8793.medicinal_herb.activity.LoginActivity;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        LinearLayout next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(v->{
            Intent intent = new Intent(JoinActivity.this, ProfileActivity.class);
            startActivity(intent);
        });
    }
}