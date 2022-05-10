package com.corporation8793.medicinal_herb;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.corporation8793.medicinal_herb.databinding.ActivityDictionaryBinding;
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient;

import java.util.Collection;

import retrofit2.Retrofit;

public class DictionaryActivity extends AppCompatActivity {
    ActivityDictionaryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_dictionary);

        binding = DataBindingUtil.setContentView(this,R.layout.activity_dictionary);
        binding.setActionBar(new ActionBar("약초사전", R.color.green));

        Log.e("check",RestClient.CATEGORY_DICTIONARY);

        RestClient restClient = RestClient.INSTANCE;
        


    }
}