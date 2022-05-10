package com.corporation8793.medicinal_herb

import android.graphics.drawable.GradientDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.corporation8793.medicinal_herb.databinding.ActivityChitchatBinding

class ChitchatActivity : AppCompatActivity() {
    lateinit var binding : ActivityChitchatBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    fun init(){
        binding = DataBindingUtil.setContentView(this,R.layout.activity_chitchat)
        binding.setActionBar(ActionBar("약초 수다방",R.color.green))


    }
}