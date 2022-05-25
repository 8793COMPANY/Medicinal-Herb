package com.corporation8793.medicinal_herb.activity.main

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout.VERTICAL
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.corporation8793.medicinal_herb.Common
import com.corporation8793.medicinal_herb.MySharedPreferences
import com.corporation8793.medicinal_herb.dto.ActionBar
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.activity.join.JoinActivity
import com.corporation8793.medicinal_herb.adapter.CommentAdapter
import com.corporation8793.medicinal_herb.adapter.QnaAdapter
import com.corporation8793.medicinal_herb.databinding.ActivityChitchatBinding
import com.corporation8793.medicinal_herb.databinding.ActivityChitchatDetailBinding
import com.corporation8793.medicinal_herb.databinding.ActivityFarmDetailBinding
import com.corporation8793.medicinal_herb.decoration.FarmDecoration
import com.corporation8793.medicinal_herb.decoration.QnaDecoration
import com.corporation8793.medicinal_herb.dto.CommentItem
import com.corporation8793.medicinal_herb.dto.QnaItem
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Comment
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Post
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.User
import com.corporation8793.medicinal_herb.herb_wp.rest.repository.BoardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChitchatDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityChitchatDetailBinding
    var datas = mutableListOf<CommentItem>()
    lateinit var commentAdapter : CommentAdapter
    lateinit var prefs : MySharedPreferences


    val qna_datas = mutableListOf<QnaItem>()
    val free_board_datas = mutableListOf<QnaItem>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    fun init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chitchat_detail)
        binding.setActionBar(ActionBar("", R.color.deep_green))

        prefs = MySharedPreferences(applicationContext)

        binding.actionBar.backHome.setOnClickListener {
            finish()
        }

        binding.registerBtn.setOnClickListener {
            createComment(intent.getStringExtra("id")!!,"0",binding.commentInputBox.text.toString())
        }

        if (!intent.getStringExtra("img").equals("0")) {
            Glide.with(this).load(intent.getStringExtra("img")).into(binding.qnaImg)
        }


        binding.qnaDetailText.text = intent.getStringExtra("content")

        binding.qnaImg.clipToOutline = true

        val display : DisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(display)
        val height : Int =  (display.heightPixels / 8.5).toInt()

        commentAdapter = CommentAdapter(this,height);
        binding.commentList.adapter = commentAdapter

        val lm = LinearLayoutManager(this)
        binding.commentList.layoutManager = lm

        commentSetting()


    }

    fun commentSetting(){
        GlobalScope.launch(Dispatchers.Default) {
            Common().dataSetting(applicationContext,intent.getStringExtra("id")!!,datas,commentAdapter,binding.commentCount)
        }

    }



    fun createComment(id : String, parent : String, comment : String) {
        GlobalScope.launch(Dispatchers.Default) {
            val testId = prefs.getString("id","hello")
            val testPw = prefs.getString("pw","1234")
            val basicAuth = Credentials.basic(testId, testPw)
            val boardRepository = BoardRepository(basicAuth)


            println("====== UsersRU             ======")
            println("------ isValid             ------")
            try {

                val isValid = boardRepository.createComment(id,parent,comment)

            } catch (e: Exception) {
                Log.e("e", e.toString())
                Log.e("e", e.message.toString())
            }

            GlobalScope.launch(Dispatchers.Main) {
                binding.commentInputBox.setText("")
                commentSetting()
            }


        }


    }

}