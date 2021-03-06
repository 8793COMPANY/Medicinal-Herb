package com.corporation8793.medicinal_herb.activity.main

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.corporation8793.medicinal_herb.decoration.FarmDecoration
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.adapter.FarmAdapter
import com.corporation8793.medicinal_herb.databinding.ActivityFarmBinding
import com.corporation8793.medicinal_herb.dto.ActionBar
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

class FarmActivity : AppCompatActivity() {
    lateinit var binding : ActivityFarmBinding
    lateinit var farmAdapter : FarmAdapter
    lateinit var divider : FarmDecoration

    val datas = mutableListOf<FarmItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

    }

    fun init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_farm)
        binding.setActionBar(ActionBar("방방곡곡 약초농장", R.color.deep_green))

        binding.actionBar.backHome.setOnClickListener {
            finish()
        }

        val display : DisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(display)
        val height : Int =  (display.heightPixels / 6).toInt()

        farmAdapter = FarmAdapter(this,height)
        binding.farmList.adapter = farmAdapter

        val lm = LinearLayoutManager(this)
        binding.farmList.layoutManager = lm

        divider = FarmDecoration(10,resources.getColor(R.color.green))


        binding.farmList.addItemDecoration(divider)

        GlobalScope.launch(Dispatchers.Default) {

            val check: List<Post>? = RestClient.boardService.retrievePostInCategories("100", "1", "desc", RestClient.CATEGORY_FARM).execute().body()
            Log.e("check", check!!.size.toString())
            datas.apply {
                check.forEach {
                    var img = ""
                    if (it.featured_media == "0")
                        img = "0"
                    else {
                        img = RestClient.boardService.retrieveMedia(it.featured_media).execute().body()!!.guid.rendered
                        Log.e("response",img)
                    }


                        var owner_name = it.acf.owner_name
                        if(owner_name == null)
                            owner_name = it.title.rendered
                        val comment_size = RestClient.boardService.retrieveAllComment(it.id).execute().body()!!.size
                    Log.e("response",comment_size.toString())
                        add(FarmItem(it.id,comment_size,R.drawable.herb_basic_user_icon,img,it.title.rendered,owner_name,it.excerpt.rendered))



                    farmAdapter.datas = datas
                    GlobalScope.launch(Dispatchers.Main) {    // 2
                        farmAdapter.notifyDataSetChanged()
                    }
                    }
                }




            }





//        val posting : Call<List<Post>> = RestClient.boardService.retrievePostInCategories("100","1","desc", RestClient.CATEGORY_FARM)
//
//        posting.enqueue(object : Callback<List<Post>> {
//            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
//                val check : List<Post>? = response.body()
//                var repo =""
//                datas.apply {
//                check?.forEach{ it->
//                    repo += "$it\n-----------------------"
//                    var owner_name = it.acf.owner_name
//                    if(owner_name == null)
//                        owner_name = it.title.rendered
//
//                        add(FarmItem(it.id,R.drawable.herb_basic_user_icon,it.title.rendered,owner_name,it.excerpt.rendered))
//
//
//                    }
//
//                    farmAdapter.datas = datas
//                    farmAdapter.notifyDataSetChanged()
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


        val comment : Call<List<Comment>> = RestClient.boardService.retrieveAllComment((144).toString())

        comment.enqueue(object : Callback<List<Comment>> {
            override fun onResponse(call: Call<List<Comment>>, response: Response<List<Comment>>) {
                val check : List<Comment>? = response.body()
                var repo =""

                check?.forEach{ it->
                    repo += "$it\n-----------------------"
                }
                Log.e("comment 설명 : ",repo)

            }

            override fun onFailure(call: Call<List<Comment>>, t: Throwable) {
                Log.e("t",t.message.toString())
            }

        })





    }

}