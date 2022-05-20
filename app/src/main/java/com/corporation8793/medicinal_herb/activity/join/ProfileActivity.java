package com.corporation8793.medicinal_herb.activity.join;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.corporation8793.medicinal_herb.R;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        LinearLayout save_btn = findViewById(R.id.save_btn);
        ImageView user_img  = findViewById(R.id.user_img);
        ImageView input_btn = findViewById(R.id.input_btn);
        EditText introduction = findViewById(R.id.introduction);


        introduction.requestFocus();
        user_img.setOnClickListener(v->{

        });

        input_btn.setOnClickListener(v->{
            if (introduction.isEnabled()){
                introduction.setEnabled(false);

            }else{
                introduction.setEnabled(true);
                introduction.requestFocus();
            }
        });

        save_btn.setOnClickListener(v->{
            Intent intent = new Intent(ProfileActivity.this, SetNotiActivity.class);
            startActivity(intent);
        });


    }
}