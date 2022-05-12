package com.corporation8793.medicinal_herb.activity.main

import android.os.Bundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.adapter.FarmAdapter
import com.corporation8793.medicinal_herb.databinding.ActivityEventBinding
import com.corporation8793.medicinal_herb.databinding.ActivityFarmBinding
import com.corporation8793.medicinal_herb.dto.ActionBar
import com.corporation8793.medicinal_herb.dto.FarmItem
import com.corporation8793.medicinal_herb.dto.HerbItem
import com.corporation8793.medicinal_herb.fragment.EventListFragment

class FarmActivity : AppCompatActivity() {
    lateinit var binding : ActivityFarmBinding
    lateinit var farmAdapter : FarmAdapter

    val datas = mutableListOf<FarmItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

    }

    fun init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_farm)
        binding.setActionBar(ActionBar("방방곡곡 약초농장", R.color.black))

        val display : DisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(display)
        val height : Int =  (display.heightPixels / 6).toInt()

        farmAdapter = FarmAdapter(this,height)
        binding.farmList.adapter = farmAdapter

        val lm = LinearLayoutManager(this)
        binding.farmList.layoutManager = lm

        datas.apply {
            add(FarmItem(R.drawable.herb_basic_user_icon,"농장1","김대길","안녕하세요. 딸기 팝니다."))
            add(FarmItem(R.drawable.herb_basic_user_icon,"농장2","김홍길","안녕하세요. 복숭아 팝니다."))
            add(FarmItem(R.drawable.herb_basic_user_icon,"농장3","김길","안녕하세요. 사과 팝니다."))

            farmAdapter.datas = datas
            farmAdapter.notifyDataSetChanged()
        }


    }

}