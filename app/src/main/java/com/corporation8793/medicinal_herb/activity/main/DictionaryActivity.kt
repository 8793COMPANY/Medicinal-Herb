package com.corporation8793.medicinal_herb.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.corporation8793.medicinal_herb.adapter.HerbAdapter
import com.corporation8793.medicinal_herb.dto.ActionBar
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.databinding.ActivityDictionaryBinding
import com.corporation8793.medicinal_herb.dto.HerbItem

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
        binding.setActionBar(ActionBar("약초사전", R.color.green))

        val display : DisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(display)
        val height : Int =  (display.heightPixels / 4.5).toInt()

        herbAdapter = HerbAdapter(this,height)
        binding.herbList.adapter = herbAdapter

        val lm = GridLayoutManager(this,2)
        binding.herbList.layoutManager = lm

        datas.apply {
            add(HerbItem(R.drawable.intro1,"둥글레"))
            add(HerbItem(R.drawable.intro1,"구기차"))
            add(HerbItem(R.drawable.intro1,"감초"))
            add(HerbItem(R.drawable.intro1,"결명자"))

            herbAdapter.datas = datas
            herbAdapter.notifyDataSetChanged()
        }






    }
}