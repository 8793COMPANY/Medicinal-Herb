package com.corporation8793.medicinal_herb.activity.join;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.corporation8793.medicinal_herb.R;
import com.corporation8793.medicinal_herb.activity.LoginActivity;

import java.util.regex.Pattern;

public class JoinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        EditText id_input_box  = findViewById(R.id.id_input_box);
        EditText pw_input_box  = findViewById(R.id.pw_input_box);
        EditText pw_input_check_box  = findViewById(R.id.pw_check_input_box);
        CheckBox access_term_check = findViewById(R.id.access_term_check);

        LinearLayout next_btn = findViewById(R.id.next_btn);
        next_btn.setOnClickListener(v->{
            if(id_input_box.getText().toString().trim().equals("")){
                Toast.makeText(getApplicationContext(),"아이디를 입력해주세요.", Toast.LENGTH_SHORT).show();
            } else if (!pw_input_box.getText().toString().equals(pw_input_check_box.getText().toString())){
                Toast.makeText(getApplicationContext(),"비밀번호가 일치하는지 다시 한번 확인해주세요.", Toast.LENGTH_SHORT).show();
            } else if (!access_term_check.isChecked()){
                Toast.makeText(getApplicationContext(),"이용약관을 동의해주세요.", Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(JoinActivity.this, ProfileActivity.class);
                startActivity(intent);
            }

        });

        InputFilter filterAlphaNum = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Pattern ps = Pattern.compile("^[a-zA-Z0-9]+$");
                if (!ps.matcher(source).matches()) {
                    Toast.makeText(getApplicationContext(),"영어와 숫자만 적어주세요.",Toast.LENGTH_SHORT).show();
                    return id_input_box.getText().toString()+"";
                }
                return null;
            }
        };

// 정규표현식만 적용하는 경우
        id_input_box.setFilters(new InputFilter[] {
                filterAlphaNum
        });
    }
}