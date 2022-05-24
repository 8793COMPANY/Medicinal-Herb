package com.corporation8793.medicinal_herb.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.corporation8793.medicinal_herb.R
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.view.View
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.corporation8793.medicinal_herb.MySharedPreferences
import com.corporation8793.medicinal_herb.activity.join.JoinActivity
import com.corporation8793.medicinal_herb.activity.main.MainActivity2
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.repository.BoardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials

class LoginActivity : AppCompatActivity() {
    lateinit var prefs : MySharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        prefs = MySharedPreferences(applicationContext)
        val join_btn = findViewById<Button>(R.id.join_btn)
        val id_input_box = findViewById<EditText>(R.id.id_input_box)
        val pw_input_box = findViewById<EditText>(R.id.pw_input_box)
        val login_btn = findViewById<LinearLayout>(R.id.login_btn)
        join_btn.setOnClickListener { v: View? ->
            val intent = Intent(this@LoginActivity, JoinActivity::class.java)
            startActivity(intent)
        }
        login_btn.setOnClickListener { v: View? ->
            if (id_input_box.text.toString().trim().equals("")){
                Toast.makeText(applicationContext,"아이디를 입력해주세요.",Toast.LENGTH_SHORT).show()
            }else if(pw_input_box.text.toString().trim().equals("")){
                Toast.makeText(applicationContext,"비밀번호를 입력해주세요.",Toast.LENGTH_SHORT).show()
            }else {
                GlobalScope.launch(Dispatchers.Default) {
                    id_pw_check(id_input_box.text.toString(), pw_input_box.text.toString())

                }
            }

//


//            RestClient.nonceService
        }
    }

    fun id_pw_check(id : String, pw : String) {
        val testId = id
        val testPw = pw
        val basicAuth = Credentials.basic(testId, testPw)
        val boardRepository = BoardRepository(basicAuth)


        println("====== UsersRU             ======")
        println("------ isValid             ------")
        try {
            val isValid = boardRepository.validationUser()

            println("retrieve User 닉네임(ID) : ${isValid.second?.name}(${isValid.second?.id})")
            println("retrieve Status : ${isValid.first}\n")
            println("retrieve url : ${isValid.second?.url}\n")


            val retrieveUser = boardRepository.retrieveUser(isValid.second?.id)
            Log.e("user", retrieveUser.second?.name.toString())




            when (isValid.first) {
                "200" -> {
                    finish()
                    prefs.setString("user_name",isValid.second?.name.toString())
                    prefs.setString("id",testId)
                    prefs.setString("pw",testPw)
                    prefs.setString("img",isValid.second?.url.toString())
                    prefs.setString("introdution",isValid.second?.description.toString())
                    Log.e("description",isValid.second?.description.toString())
                    val intent = Intent(this@LoginActivity, MainActivity2::class.java)
                    startActivity(intent)
                }

            }
        }catch (e : Exception){
            Log.e("e",e.toString())

            GlobalScope.launch(Dispatchers.Main) {
                if (e.message!!.contains("Path parameter \"id\" value must not be null.")){
                    Toast.makeText(applicationContext,"아이디와 비밀번호를 다시 한번 확인해주세요",Toast.LENGTH_SHORT).show()
                }
            }
            Log.e("e",e.message.toString())
        }


    }


}