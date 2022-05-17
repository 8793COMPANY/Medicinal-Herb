package com.corporation8793.medicinal_herb.activity.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.databinding.ActivityEventBinding
import com.corporation8793.medicinal_herb.dto.ActionBar
import com.corporation8793.medicinal_herb.fragment.EventListFragment

class EventActivity : AppCompatActivity() {
    lateinit var binding : ActivityEventBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

    }

    fun init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_event)
        binding.setActionBar(ActionBar("이벤트", R.color.deep_green))
        binding.actionBar.backHome.setOnClickListener {
            finish()
            var intent : Intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        replaceFragment()

    }

    fun replaceFragment() {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, EventListFragment())
        transaction.commit()
    }

}