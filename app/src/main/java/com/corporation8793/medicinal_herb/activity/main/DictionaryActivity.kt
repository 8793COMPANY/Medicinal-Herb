package com.corporation8793.medicinal_herb.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.corporation8793.medicinal_herb.decoration.HerbDecoration
import com.corporation8793.medicinal_herb.adapter.HerbAdapter
import com.corporation8793.medicinal_herb.dto.ActionBar
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.databinding.ActivityDictionaryBinding
import com.corporation8793.medicinal_herb.dto.HerbItem
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Post
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

        val display : DisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(display)
        val height : Int =  (display.heightPixels / 4.5).toInt()

        herbAdapter = HerbAdapter(this,height)
        binding.herbList.adapter = herbAdapter

        val lm = GridLayoutManager(this,2)
        binding.herbList.layoutManager = lm

        binding.herbList.addItemDecoration(HerbDecoration(10))

        val posting : Call<List<Post>> = RestClient.boardService.retrievePostInCategories("100","1","desc",RestClient.CATEGORY_DICTIONARY)

        posting.enqueue(object : Callback<List<Post>>{
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                val check : List<Post>? = response.body()
                var repo =""

                datas.apply {
                    check?.forEach{ it->
                        if(! it.title.rendered.equals("약초사전")) {

                            add(HerbItem(it.id, R.drawable.intro1, it.title.rendered))
                            Log.e("check",it.id)


                        }
                        Log.e("it","$it\n")

                    }

                    herbAdapter.datas = datas
                    herbAdapter.notifyDataSetChanged()
                }

//                Log.e("herb 설명 : ",repo)

            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })




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
}