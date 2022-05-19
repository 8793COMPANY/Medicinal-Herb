package com.corporation8793.medicinal_herb.activity.join;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.corporation8793.medicinal_herb.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        LinearLayout save_btn = findViewById(R.id.save_btn);
        save_btn.setOnClickListener(v->{
            Intent intent = new Intent(ProfileActivity.this, SetNotiActivity.class);
            startActivity(intent);
        });
    }
}