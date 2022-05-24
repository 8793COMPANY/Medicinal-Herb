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


        GlobalScope.launch(Dispatchers.Default) {

//            val qna_posting : Call<List<Post>> = RestClient.boardService.retrievePostInCategories("100","1","desc", RestClient.CATEGORY_QNA)
            val check: List<Comment>? = RestClient.boardService.retrieveAllComment(intent.getStringExtra("id")!!).execute().body()!!
            Log.e("check", check!!.size.toString())
            var repo =""


            datas.apply {
                check.forEach {
//                    var response = it.featured_media
                    Log.e("it.author",it.author)
                    val check: User? =  RestClient.boardService.retrieveUser("1").execute().body()
                    Log.e("check id",check!!.id)
                    Log.e("check name",check!!.name)
                    Log.e("check url",check!!.url)
                    Log.e("check",check!!.description)
                    var img = check!!.url
                    if(img.trim().equals(""))
                        img = "0"




                    Log.e("id", it.id)
                    Log.e("id", it.content.rendered)
//                        Log.e("response", response.guid.rendered)

                    add(CommentItem(img,it.author_name,it.content.rendered,it.date,0))


                }
                commentAdapter.datas = datas
                GlobalScope.launch(Dispatchers.Main) {    // 2
                    commentAdapter.notifyDataSetChanged()
                    binding.commentCount.text = "댓글 "+check!!.size
                    var content = binding.commentCount.text.toString()
                    val spannableString : SpannableString = SpannableString(content)
                    var start = 2

                    val colorGreenSpan = ForegroundColorSpan(resources.getColor(R.color.green))

                    spannableString.setSpan(colorGreenSpan,start,content.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                    binding.commentCount.text = spannableString
                }


            }


        }


//        val one_posting : Call<List<Comment>> = RestClient.boardService.retrieveAllComment(intent.getStringExtra("id").toString())
//
//        one_posting.enqueue(object : Callback<List<Comment>> {
//            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
//                val check : List<Comment>? = response.body()
//                var repo =""
//                binding.commentCount.text = "댓글 "+check!!.size
//                var content = binding.commentCount.text.toString()
//                val spannableString : SpannableString = SpannableString(content)
//                var start = 2
//
//                val colorGreenSpan = ForegroundColorSpan(resources.getColor(R.color.green))
//
//                spannableString.setSpan(colorGreenSpan,start,content.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
//
//                binding.commentCount.text = spannableString
//                datas.apply {
//
//                    check?.forEach{ it->
//                        repo += "$it\n-----------------------"
//                        Log.e("it",it.toString())
//                        add(CommentItem(0,it.author_name,it.content.rendered,it.date,0))
//
//
//                    }
//
//                    commentAdapter.datas = datas
//                    commentAdapter.notifyDataSetChanged()
//                }
//
//
//            }
//
//            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })



//        qna_datas.apply {
//            add(QnaItem(R.drawable.herb_basic_qna_icon,"안녕하세요. 얼마전 ***을 선물로 받았습니다.\n어디에 좋은지 알 수 있을까요?","1"))
//            add(QnaItem(R.drawable.herb_basic_qna_icon,"안녕하세요. 얼마전 *을 선물로 받았습니다.\n어디에 좋은지 알 수 있을까요?","2"))
//            add(QnaItem(R.drawable.herb_basic_qna_icon,"안녕하세요. 얼마전 **을 선물로 받았습니다.\n어디에 좋은지 알 수 있을까요?","3"))
//
//            qna_adapter.datas = qna_datas
//            qna_adapter.notifyDataSetChanged()
//        }



//        Log.e("id",MySharedPreferences(this).getString("id","hello")!!)
//        GlobalScope.launch(Dispatchers.Default) {
//
////            val qna_posting : Call<List<Post>> = RestClient.boardService.retrievePostInCategories("100","1","desc", RestClient.CATEGORY_QNA)
//
//
//
//            }






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


        }


    }




    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_chitchat_select)
        dialog.show()

//        dialog.findViewById<TextView>(R.id.qna).setOnClickListener{
//            dialog.dismiss()
//        }
//

        dialog.findViewById<ImageView>(R.id.cancel_btn).setOnClickListener{
            dialog.dismiss()
        }


        dialog.findViewById<ConstraintLayout>(R.id.qna_area).setOnClickListener{
            dialog.dismiss()
            var intent : Intent = Intent(this, QnaActivity::class.java)
            intent.putExtra("category","묻고 답하기")
            startActivity(intent)
        }

        dialog.findViewById<ConstraintLayout>(R.id.free_board_area).setOnClickListener{
            dialog.dismiss()
            var intent : Intent = Intent(this, QnaActivity::class.java)
            intent.putExtra("category","자유 게시판")
            startActivity(intent)
        }



        dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

//        dialog.getWindow()!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

                val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        // Generate custom width and height and
        // add to the dialog attributes
        // we multiplied the width and height by 0.5,
        // meaning reducing the size to 50%
        val mLayoutParams = WindowManager.LayoutParams()
        mLayoutParams.width = (size.x * 0.57f).toInt()
        mLayoutParams.height = (size.y * 0.3f).toInt()

        dialog.window?.attributes = mLayoutParams

    }
}