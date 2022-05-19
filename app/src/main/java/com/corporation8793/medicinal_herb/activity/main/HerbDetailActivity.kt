package com.corporation8793.medicinal_herb.activity.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.databinding.ActivityHerbDetailBinding
import com.corporation8793.medicinal_herb.dto.ActionBar
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Guid
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Media
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HerbDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityHerbDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        var media_id = ""

        val one_posting : Call<Post> = RestClient.boardService.retrieveOnePost(intent.getStringExtra("id").toString())

        one_posting.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                val check : Post? = response.body()
                Log.e("one check",check?.title?.rendered.toString())
                Log.e("check",check!!.featured_media)
                media_id = check!!.featured_media

                binding.herbDetailText.text = check?.content?.rendered.toString()
                        .replace("<p>","").replace("</p>","")
                        .replace("<ul>","").replace("</ul>","")
                        .replace("<li>","").replace("</li>","")

            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })


//        val media : Call<Array<Media>> =  RestClient.boardService.retrieveMedia(media_id)
//
//        media.enqueue(object : Callback<Array<Media>>{
//            override fun onResponse(call: Call<Array<Media>>, response: Response<Array<Media>>) {
//                var num = response.body()!!.size
////                Log.e("response",response.body()!!)
//                for (i: Int in 0 until num) {
//                    Log.e("response", response.body()!!.get(i).guid.rendered)
//                }
////                val homedateList: List<Media> = gson.fromJson(body, Array<HomeDate>::class.java).toList()
////                val medias : Guid? = response.body()?.guid
////                Log.e("response",response.body()?.guid?.rendered.toString())
//            }
//
//            override fun onFailure(call: Call<Array<Media>>, t: Throwable) {
//                Log.e("t",t.message!!)
//            }
//
//        })
    }


    fun init() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_herb_detail)
        binding.setActionBar(ActionBar(intent.getStringExtra("name"), R.color.green))
        Glide.with(this).load(intent.getStringExtra("img")).into(binding.herbDetailImg)

        binding.actionBar.backHome.setOnClickListener {
            finish()
            var intent : Intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
        }

        binding.herbDetailText.movementMethod = ScrollingMovementMethod()

    }

}