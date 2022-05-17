package com.corporation8793.medicinal_herb.activity.main

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
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Post
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

        val display : DisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(display)
        val height : Int =  (display.heightPixels / 6).toInt()

        farmAdapter = FarmAdapter(this,height)
        binding.farmList.adapter = farmAdapter

        val lm = LinearLayoutManager(this)
        binding.farmList.layoutManager = lm

        divider = FarmDecoration(10,resources.getColor(R.color.green))

        val itemDivider : DividerItemDecoration = DividerItemDecoration(this,0)
        itemDivider.setDrawable(resources.getDrawable(R.color.green))
        binding.farmList.addItemDecoration(divider)

        val posting : Call<List<Post>> = RestClient.boardService.retrievePostInCategories("100","1","desc", RestClient.CATEGORY_FARM)

        posting.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                val check : List<Post>? = response.body()
                var repo =""

                check?.forEach{ it->
                    repo += "$it\n-----------------------"
                }
                Log.e("farm 설명 : ",repo)

            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })


        datas.apply {
            add(FarmItem(R.drawable.herb_basic_user_icon,"농장1","김대길","안녕하세요. 딸기 팝니다."))
            add(FarmItem(R.drawable.herb_basic_user_icon,"농장2","김홍길","안녕하세요. 복숭아 팝니다."))
            add(FarmItem(R.drawable.herb_basic_user_icon,"농장3","김길","안녕하세요. 사과 팝니다."))

            farmAdapter.datas = datas
            farmAdapter.notifyDataSetChanged()
        }


    }

}