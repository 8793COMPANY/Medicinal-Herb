package com.corporation8793.medicinal_herb.activity.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.corporation8793.medicinal_herb.Common
import com.corporation8793.medicinal_herb.decoration.HerbDecoration
import com.corporation8793.medicinal_herb.adapter.HerbAdapter
import com.corporation8793.medicinal_herb.dto.ActionBar
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.databinding.ActivityDictionaryBinding
import com.corporation8793.medicinal_herb.dto.HerbItem
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Post
import com.corporation8793.medicinal_herb.herb_wp.rest.repository.BoardRepository
import kotlinx.coroutines.*
import okhttp3.Credentials
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DictionaryActivity : AppCompatActivity() {
    lateinit var binding : ActivityDictionaryBinding
    lateinit var herbAdapter: HerbAdapter
    val datas = mutableListOf<HerbItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    fun init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dictionary)
        binding.setActionBar(ActionBar("약초사전", R.color.deep_green))

        binding.actionBar.backHome.setOnClickListener {
            finish()
//            herbAdapter.notifyDataSetChanged()
        }

        val display : DisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(display)
        val height : Int =  (display.heightPixels / 4.5).toInt()

        herbAdapter = HerbAdapter(this,height)
        binding.herbList.adapter = herbAdapter

        val lm = GridLayoutManager(this,2)
        binding.herbList.layoutManager = lm

        binding.herbList.addItemDecoration(HerbDecoration(10))
        var start = true


        var loading_dialog = Common().showLoadingDialog(this)


            GlobalScope.launch(Dispatchers.Default) {

                val check: List<Post>? = RestClient.boardService.retrievePostInCategories("100", "1", "asc", RestClient.CATEGORY_DICTIONARY).execute().body()
                Log.e("check", check!!.size.toString())
                datas.apply {
                    check.forEach {
                        if (it.featured_media != "0") {
                            val response = RestClient.boardService.retrieveMedia(it.featured_media).execute().body()!!
                            Log.e("id", it.id)
                            Log.e("id", it.title.rendered)
                            Log.e("response", response.guid.rendered)
                            add(HerbItem(it.id, response.guid.rendered, it.title.rendered))
                        }
                    }
                    herbAdapter.datas = datas
                    GlobalScope.launch(Dispatchers.Main) {    // 2
                       herbAdapter.notifyDataSetChanged()
                        loading_dialog.dismiss()
                    }


                }


            }







//        val posting : Call<List<Post>> = RestClient.boardService.retrievePostInCategories("100","1","desc",RestClient.CATEGORY_DICTIONARY)
//
//        posting.enqueue(object : Callback<List<Post>>{
//            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
//                val check : List<Post>? = response.body()
//                var repo =""
//
//                datas.apply {
//                    check?.forEach{ it->
//                        if(! it.title.rendered.equals("약초사전")) {
//
//
//
//
//
//
//
//
//
//                            Log.e("check",it.id)
//
//
//                        }
//                        Log.e("it","$it\n")
//
//                    }
//
//                    herbAdapter.datas = datas
//                    herbAdapter.notifyDataSetChanged()
//
//
//                }
//
//
////                Log.e("herb 설명 : ",repo)
//
//            }
//
//            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//
//        })




//        datas.apply {
//            add(HerbItem(R.drawable.intro1,"둥글레"))
//            add(HerbItem(R.drawable.intro1,"구기차"))
//            add(HerbItem(R.drawable.intro1,"감초"))
//            add(HerbItem(R.drawable.intro1,"결명자"))
//
//            herbAdapter.datas = datas
//            herbAdapter.notifyDataSetChanged()
//        }






    }

    override fun onResume() {
        super.onResume()
    }
}