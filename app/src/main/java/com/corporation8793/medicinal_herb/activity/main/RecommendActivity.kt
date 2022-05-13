package com.corporation8793.medicinal_herb.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.adapter.RecommendAdapter
import com.corporation8793.medicinal_herb.databinding.ActivityRecommendBinding
import com.corporation8793.medicinal_herb.dto.ActionBar

class RecommendActivity : AppCompatActivity() {

    lateinit var binding : ActivityRecommendBinding
    lateinit var symptomAdapter : RecommendAdapter

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
        symptomAdapter = RecommendAdapter(this,buttonNames)

//        binding.symptomList.adapter = symptomAdapter



//        binding.symptomList.onItemClickListener = object : AdapterView.OnItemClickListener {
//            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
//                // Get the GridView selected/clicked item text
//                Log.e("position",position.toString())
//            }
//        }



    }
}