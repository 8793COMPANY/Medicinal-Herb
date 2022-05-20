package com.corporation8793.medicinal_herb.activity.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.medicinal_herb.decoration.HerbDecoration
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.decoration.SymptomDecoration
import com.corporation8793.medicinal_herb.adapter.HerbAdapter
import com.corporation8793.medicinal_herb.adapter.SymptomAdapter
import com.corporation8793.medicinal_herb.databinding.ActivityRecommendBinding
import com.corporation8793.medicinal_herb.dto.ActionBar
import com.corporation8793.medicinal_herb.dto.HerbItem
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecommendActivity : AppCompatActivity() {

    lateinit var binding : ActivityRecommendBinding
    lateinit var symptomAdapter : SymptomAdapter
    lateinit var herbAdapter: HerbAdapter
    lateinit var divider : SymptomDecoration

    val datas = mutableListOf<HerbItem>()

    var buttonNames = arrayOf("두통/통증완화", "혈압/심혈관", "위장/소화기능", "당뇨/신장/췌장"
            ,"간/피로회복","비뇨기/정력강화","갱년기/치매예방","눈/귀건강"
            ,"항암/면역강화","여성질환/부인과","관절/뼈건강","미용/피부질환")

    var recommendTexts = arrayOf("두통·통증 완화에 추천합니다.", " 혈압·심혈관 질환에 추천합니다.", "위장·소화기능에 추천합니다.", "당뇨·신장·췌장에 추천합니다."
            ,"간·피로회복에 추천합니다.","비뇨기·정력강화에 추천합니다.","갱년기·치매예방에 추천합니다.","눈과 귀 건강에 추천합니다."
            ,"항암 및 면역강화에 추천합니다.","여성질환 및 부인과 질환에 추천합니다.","관절 및 뼈 건강에 추천합니다.","미용 및 피부질환에 추천합니다.")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recommend)
        init()
    }

    fun init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recommend)
        binding.setActionBar(ActionBar("맞춤추천", R.color.deep_green))

        binding.actionBar.backHome.setOnClickListener {
            finish()
        }


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

        symptomAdapter.setOnItemClickListener(object : SymptomAdapter.OnItemClickListener{
            override fun onItemClick(v: View, pos: Int) {
                Log.e("pos",pos.toString())
                if (!v.isSelected) {

                    binding.symptomRecommendText.text = recommendTexts[pos]
                    symptomAdapter.getNumber(pos)
                    symptomAdapter.notifyDataSetChanged()
                    dataBySymptom(buttonNames[pos])


                }
            }

        })


        dataBySymptom(buttonNames[0])





    }

    fun hasAndKey(value:String, keywords: List<String>) : Boolean{
        keywords.forEach {
            if (value.contains(it)){
                return true
            }
        }
        return false
    }


    fun dataBySymptom(symptom : String){
        GlobalScope.launch(Dispatchers.Default) {

            val check: List<Post>? = RestClient.boardService.retrievePostInCategories("100", "1", "desc", RestClient.CATEGORY_RECOMMEND).execute().body()
            Log.e("check", check!!.size.toString())
            datas.clear()
            datas.apply {
                check.forEach {
                    if (hasAndKey(it.title.rendered,symptom.split("/")) && it.featured_media != "0") {
                        Log.e("it", it.id)
                        Log.e("it", it.featured_media)
                        Log.e("it", it.title.rendered)
                        val response = RestClient.boardService.retrieveMedia(it.featured_media).execute().body()!!
                        val start = it.title.rendered.indexOf("[")
                        val end = it.title.rendered.indexOf("]")
                        add(HerbItem(it.id, response.guid.rendered, it.title.rendered.substring(start+1, end)))
                    }
                }
                herbAdapter.datas = datas
                GlobalScope.launch(Dispatchers.Main) {    // 2
                    herbAdapter.notifyDataSetChanged()
                }


            }


        }
    }
}