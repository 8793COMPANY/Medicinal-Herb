package com.corporation8793.medicinal_herb.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.corporation8793.medicinal_herb.R
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.view.View
import android.content.Intent
import com.corporation8793.medicinal_herb.activity.join.JoinActivity
import com.corporation8793.medicinal_herb.activity.main.MainActivity2
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val join_btn = findViewById<Button>(R.id.join_btn)
        val id_input_box = findViewById<EditText>(R.id.id_input_box)
        val pw_input_box = findViewById<EditText>(R.id.pw_input_box)
        val login_btn = findViewById<LinearLayout>(R.id.login_btn)
        join_btn.setOnClickListener { v: View? ->
            val intent = Intent(this@LoginActivity, JoinActivity::class.java)
            startActivity(intent)
        }
        login_btn.setOnClickListener { v: View? ->
            val intent = Intent(this@LoginActivity, MainActivity2::class.java)
            startActivity(intent)
//            RestClient.nonceService
        }
    }
}