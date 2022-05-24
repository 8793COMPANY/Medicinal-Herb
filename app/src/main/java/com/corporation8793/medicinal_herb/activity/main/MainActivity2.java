package com.corporation8793.medicinal_herb.activity.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.corporation8793.medicinal_herb.Common;
import com.corporation8793.medicinal_herb.MySharedPreferences;
import com.corporation8793.medicinal_herb.R;
import com.corporation8793.medicinal_herb.activity.join.ProfileActivity;
import com.corporation8793.medicinal_herb.databinding.ActivityMain2Binding;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;



public class MainActivity2 extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMain2Binding binding;
    float waitTime = 0L;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


//        binding.appBarMain.toolbar.setNavigationIcon(R.drawable.main_menu_icon);
//        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;



        MySharedPreferences preferences = new MySharedPreferences(this);
        Common common = new Common();


        View header = navigationView.getHeaderView(0);

        // header에 있는 리소스 가져오기
        TextView text = (TextView) header.findViewById(R.id.user_name);
        text.setText(preferences.getString("user_name","산야초"));

        ImageView img = header.findViewById(R.id.user_img);
        if (!preferences.getString("img","0").equals("0"))
            Glide.with(this).load(preferences.getString("img","0")).into(img);

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

           if (id == R.id.nav_access_term || id == R.id.nav_personal_data || id == R.id.nav_open_source || id == R.id.nav_app_version){
               drawer.close();
               common.showAccessTerms(this);
           }else{
               drawer.close();
               Intent intent = new Intent(MainActivity2.this, ProfileActivity.class);
               intent.putExtra("type","edit");
               intent.putExtra("user_name",preferences.getString("user_name","산야초"));
               startActivity(intent);
           }


            return true;
        });


        setSupportActionBar(binding.appBarMain.toolbar);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        mAppBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
//                .setOpenableLayout(drawer)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);



        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(false);            //액션바 아이콘을 업 네비게이션 형태로 표시합니다.
        actionBar.setDisplayShowTitleEnabled(false);        //액션바에 표시되는 제목의 표시유무를 설정합니다.
        actionBar.setDisplayShowHomeEnabled(false);            //홈 아이콘을 숨김처리합니다.


        //layout을 가지고 와서 actionbar에 포팅을 시킵니다.
        LayoutInflater inflater = (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
        View actionbar = inflater.inflate(R.layout.custom_actionbar, null);

        actionBar.setCustomView(actionbar);

        getSupportActionBar().getCustomView().findViewById(R.id.action_bar_menu_btn).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Log.e("hi","in!");
                drawer.openDrawer(GravityCompat.START);
            }
        });
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main_activity2, menu);
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis() - waitTime >=1500 ) {
            waitTime = System.currentTimeMillis();
            Toast.makeText(this,"뒤로가기 버튼을 한번 더 누르면 종료됩니다.",Toast.LENGTH_SHORT).show();
        } else {
            finish(); // 액티비티 종료
        }
    }
}