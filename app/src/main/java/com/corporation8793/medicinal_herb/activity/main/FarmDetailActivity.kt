package com.corporation8793.medicinal_herb.activity.main

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.DisplayMetrics
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.corporation8793.medicinal_herb.Common
import com.corporation8793.medicinal_herb.MySharedPreferences
import com.corporation8793.medicinal_herb.decoration.FarmDecoration
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.adapter.CommentAdapter
import com.corporation8793.medicinal_herb.databinding.ActivityFarmDetailBinding
import com.corporation8793.medicinal_herb.dto.*
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

class FarmDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityFarmDetailBinding
    lateinit var commentAdapter : CommentAdapter
    lateinit var divider : FarmDecoration
    lateinit var prefs : MySharedPreferences



    val comment_list = mutableMapOf<String,Array<Int>>()

    var count = 0

    val datas = mutableListOf<CommentItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()


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

    fun init(){
        prefs = MySharedPreferences(applicationContext)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_farm_detail)
        binding.farmName.text = intent.getStringExtra("farm_name")
        binding.likeBtn.setOnClickListener {
            if (binding.likeBtn.isSelected){
                binding.likeBtn.isSelected = false
                binding.likeBtn.background = resources.getDrawable(R.drawable.my_like_off_icon)
                prefs.setString(intent.getStringExtra("id")!!,"no")
            }else{
                binding.likeBtn.isSelected = true
                binding.likeBtn.background = resources.getDrawable(R.drawable.my_like_on_icon)
                prefs.setString(intent.getStringExtra("id")!!,"like")
            }
        }

        if(prefs.getString(intent.getStringExtra("id")!!,"no").equals("like")){
            binding.likeBtn.isSelected = true
            binding.likeBtn.background = resources.getDrawable(R.drawable.my_like_on_icon)
        }

//        binding.actionBar.backHome.setOnClickListener {
//            finish()
//        }

        binding.registerBtn.setOnClickListener {
            if (!binding.commentInputBox.text.toString().trim().equals("")) {
                createComment(intent.getStringExtra("id")!!, "0", binding.commentInputBox.text.toString())
            }else{
                Toast.makeText(applicationContext,"글을 입력해주세요.",Toast.LENGTH_SHORT).show()
            }
        }

        val display : DisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(display)
        val height : Int =  (display.heightPixels / 8.5).toInt()

        commentAdapter = CommentAdapter(this,height)
        binding.commentList.adapter = commentAdapter
//        binding.commentList.isNestedScrollingEnabled = false



        val lm = LinearLayoutManager(this)
        binding.commentList.layoutManager = lm

        Glide.with(this).load(intent.getStringExtra("farm_img")).into(binding.farmImg)



        val farm_info : Call<Post> = RestClient.boardService.retrieveOnePost(intent.getStringExtra("id")!!)

        farm_info.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {


                val check : Post? = response.body()

                var owner_name = check!!.acf.owner_name
                if(owner_name == null)
                    owner_name = "산야초"

                binding.farmDetailText.text = Common().replaceText(check!!.title.rendered + "\n" + check!!.acf.owner_name + "\n" +
                        check!!.content.rendered)

            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e("t",t.message.toString())
            }

        })

        commentSetting()


    }

    fun commentSetting(){
        GlobalScope.launch(Dispatchers.Default) {
            datas.clear()
            Common().dataSetting(applicationContext,intent.getStringExtra("id")!!,datas,commentAdapter,binding.commentCount)
        }
    }



}