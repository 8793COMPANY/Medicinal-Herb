package com.corporation8793.medicinal_herb.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.corporation8793.medicinal_herb.decoration.HerbDecoration
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.decoration.SymptomDecoration
import com.corporation8793.medicinal_herb.adapter.HerbAdapter
import com.corporation8793.medicinal_herb.adapter.SymptomAdapter
import com.corporation8793.medicinal_herb.databinding.ActivityRecommendBinding
import com.corporation8793.medicinal_herb.dto.ActionBar
import com.corporation8793.medicinal_herb.dto.HerbItem

class RecommendActivity : AppCompatActivity() {

    lateinit var binding : ActivityRecommendBinding
    lateinit var symptomAdapter : SymptomAdapter
    lateinit var herbAdapter: HerbAdapter
    lateinit var divider : SymptomDecoration

    val datas = mutableListOf<HerbItem>()

    var buttonNames = arrayOf("두통/통증완화", "혈압/심혈관", "위장/소화기능", "당뇨/신장/췌장"
            ,"간/피로회복","비뇨기/정력강화","갱년기/치매예방","눈/귀건강"
            ,"항암/면역강화","여성질환/부인과","관절/뼈건강","미용/피부질환")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend)
        init()
    }

    fun init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recommend)
        binding.setActionBar(ActionBar("맞춤추천", R.color.green))


        val display : DisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(display)
        val height : Int =  ((display.heightPixels * 0.25) /5).toInt()

        symptomAdapter = SymptomAdapter(buttonNames,height)
        herbAdapter = HerbAdapter(this,(display.heightPixels / 4.5).toInt())

        binding.symptomList.adapter = symptomAdapter
        binding.symptomHerbList.adapter = herbAdapter

        divider = SymptomDecoration(10)

        val lm = GridLayoutManager(this,3)
        binding.symptomList.layoutManager = lm
        binding.symptomHerbList.layoutManager = GridLayoutManager(this,2)

        binding.symptomList.addItemDecoration(divider)
        binding.symptomHerbList.addItemDecoration(HerbDecoration(10))


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