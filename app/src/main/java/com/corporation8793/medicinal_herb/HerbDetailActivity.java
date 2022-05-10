package com.corporation8793.medicinal_herb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.util.Log;

import com.corporation8793.medicinal_herb.databinding.ActivityHerbDetailBinding;
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient;

public class HerbDetailActivity extends AppCompatActivity {

    ActivityHerbDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this,R.layout.activity_herb_detail);
        binding.setActionBar(new ActionBar("둥글레", R.color.black));

        Log.e("check", RestClient.CATEGORY_DICTIONARY);

        RestClient restClient = RestClient.INSTANCE;


    }
}