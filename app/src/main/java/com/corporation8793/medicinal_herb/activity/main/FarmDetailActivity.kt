package com.corporation8793.medicinal_herb.activity.main

import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.corporation8793.medicinal_herb.decoration.FarmDecoration
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.adapter.CommentAdapter
import com.corporation8793.medicinal_herb.adapter.FarmAdapter
import com.corporation8793.medicinal_herb.databinding.ActivityFarmDetailBinding
import com.corporation8793.medicinal_herb.dto.ActionBar
import com.corporation8793.medicinal_herb.dto.CommentItem
import com.corporation8793.medicinal_herb.dto.FarmItem
import com.corporation8793.medicinal_herb.dto.HerbItem
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Comment
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FarmDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityFarmDetailBinding
    lateinit var commentAdapter : CommentAdapter
    lateinit var divider : FarmDecoration

    val datas = mutableListOf<CommentItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

    }

    fun init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_farm_detail)
        binding.farmName.text = intent.getStringExtra("farm_name")
        binding.likeBtn.setOnClickListener {
            if (binding.likeBtn.isSelected){
                binding.likeBtn.isSelected = false
                binding.likeBtn.background = resources.getDrawable(R.drawable.my_like_off_icon)
            }else{
                binding.likeBtn.isSelected = true
                binding.likeBtn.background = resources.getDrawable(R.drawable.my_like_on_icon)
            }
        }

//        binding.actionBar.backHome.setOnClickListener {
//            finish()
//        }

        val display : DisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(display)
        val height : Int =  (display.heightPixels / 8.5).toInt()

        commentAdapter = CommentAdapter(this,height)
        binding.commentList.adapter = commentAdapter
//        binding.commentList.isNestedScrollingEnabled = false



        val lm = LinearLayoutManager(this)
        binding.commentList.layoutManager = lm

        Glide.with(this).load(intent.getStringExtra("farm_img")).into(binding.farmImg)




//
//        divider = FarmDecoration(10,resources.getColor(R.color.green))
//
//
//        binding.farmList.addItemDecoration(divider)

//        val posting : Call<List<Post>> = RestClient.boardService.retrievePostInCategories("100","1","desc", RestClient.CATEGORY_FARM)
//
//        posting.enqueue(object : Callback<List<Post>> {
//            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
//                val check : List<Post>? = response.body()
//                var repo =""
//                datas.apply {
//                check?.forEach{ it->
//                    repo += "$it\n-----------------------"
//
//
//
//
//                    }
//
//                    commentAdapter.datas = datas
//                    commentAdapter.notifyDataSetChanged()
//                }
//                Log.e("farm 설명 : ",repo)
//
//            }
//
//            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
//                Log.e("t",t.message.toString())
//            }
//
//        })


        val farm_info : Call<Post> = RestClient.boardService.retrieveOnePost(intent.getStringExtra("id")!!)

        farm_info.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val check : Post? = response.body()

                var owner_name = check!!.acf.owner_name
                if(owner_name == null)
                    owner_name = "산야초"

                binding.farmDetailText.text = check!!.title.rendered + "\n" + check!!.acf.owner_name + "\n" +
                        check!!.content.rendered.replace("<p>","").replace("</p>","")

            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Log.e("t",t.message.toString())
            }

        })




        val comment : Call<List<Comment>> = RestClient.boardService.retrieveAllComment(intent.getStringExtra("id")!!)

        comment.enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                val check : List<Comment>? = response.body()

                var repo =""
                binding.commentCount.text = "댓글 "+check!!.size
                datas.apply {

                check?.forEach{ it->

                    repo += "$it\n-----------------------"
                    add(CommentItem(0,it.author_name,it.content.rendered,it.date,"comment"))
                }

                    commentAdapter.datas = datas
                    commentAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.e("t",t.message.toString())
            }

        })





    }

}