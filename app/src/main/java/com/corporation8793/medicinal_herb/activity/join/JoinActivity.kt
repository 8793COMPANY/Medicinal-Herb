package com.corporation8793.medicinal_herb.activity.join


import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.InputFilter
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.corporation8793.medicinal_herb.Common
import com.corporation8793.medicinal_herb.R
import java.util.regex.Pattern

class JoinActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        val id_input_box = findViewById<EditText>(R.id.id_input_box)
        val pw_input_box = findViewById<EditText>(R.id.pw_input_box)
        val pw_input_check_box = findViewById<EditText>(R.id.pw_check_input_box)
        val access_term_check = findViewById<CheckBox>(R.id.access_term_check)
        val next_btn = findViewById<LinearLayout>(R.id.next_btn)
        val access_terms_box = findViewById<ConstraintLayout>(R.id.access_terms_box)
        next_btn.setOnClickListener { v: View? ->
            if (id_input_box.text.toString().trim { it <= ' ' } == "") {
                Toast.makeText(applicationContext, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else if (pw_input_box.text.toString() != pw_input_check_box.text.toString()) {
                Toast.makeText(applicationContext, "비밀번호가 일치하는지 다시 한번 확인해주세요.", Toast.LENGTH_SHORT).show()
            } else if (!access_term_check.isChecked) {
                Toast.makeText(applicationContext, "이용약관을 동의해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this@JoinActivity, ProfileActivity::class.java)
                intent.putExtra("type","join")
                intent.putExtra("id",id_input_box.text.toString())
                intent.putExtra("pw",pw_input_box.text.toString())
                startActivity(intent)
            }
        }
        val filterAlphaNum = InputFilter { source, start, end, dest, dstart, dend ->
            val ps = Pattern.compile("^[a-zA-Z0-9]+$")
            if (!ps.matcher(source).matches()) {
                Toast.makeText(applicationContext, "영어와 숫자만 적어주세요.", Toast.LENGTH_SHORT).show()
            }
            null
        }

        access_terms_box.setOnClickListener{
            Common().showAccessTerms(this)
        }

// 정규표현식만 적용하는 경우
        id_input_box.filters = arrayOf(
                filterAlphaNum
        )




    }







//        dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

//        dialog.getWindow()!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);



}