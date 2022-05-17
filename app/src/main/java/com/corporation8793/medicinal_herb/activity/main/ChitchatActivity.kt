package com.corporation8793.medicinal_herb.activity.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.corporation8793.medicinal_herb.dto.ActionBar
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.adapter.QnaAdapter
import com.corporation8793.medicinal_herb.databinding.ActivityChitchatBinding
import com.corporation8793.medicinal_herb.dto.HerbItem
import com.corporation8793.medicinal_herb.dto.QnaItem

class ChitchatActivity : AppCompatActivity() {
    lateinit var binding : ActivityChitchatBinding

    lateinit var qna_adapter : QnaAdapter
    lateinit var free_board_adapter : QnaAdapter

    val qna_datas = mutableListOf<QnaItem>()
    val free_board_datas = mutableListOf<QnaItem>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    fun init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chitchat)
        binding.setActionBar(ActionBar("약초 수다방", R.color.deep_green))

        val display : DisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(display)
        val height : Int =  (display.heightPixels / 8.5).toInt()


        qna_adapter = QnaAdapter(this,height)
        free_board_adapter = QnaAdapter(this,height)

        binding.qnaList.adapter = qna_adapter
        binding.freeBoardList.adapter = free_board_adapter


        val lm = LinearLayoutManager(this)
        val lm2 = LinearLayoutManager(this)
        binding.qnaList.layoutManager = lm
        binding.freeBoardList.layoutManager = lm2

        qna_datas.apply {
            add(QnaItem(R.drawable.herb_basic_qna_icon,"안녕하세요. 얼마전 ***을 선물로 받았습니다.\n어디에 좋은지 알 수 있을까요?","1"))
            add(QnaItem(R.drawable.herb_basic_qna_icon,"안녕하세요. 얼마전 *을 선물로 받았습니다.\n어디에 좋은지 알 수 있을까요?","2"))
            add(QnaItem(R.drawable.herb_basic_qna_icon,"안녕하세요. 얼마전 **을 선물로 받았습니다.\n어디에 좋은지 알 수 있을까요?","3"))

            qna_adapter.datas = qna_datas
            qna_adapter.notifyDataSetChanged()
        }


        free_board_datas.apply {
            add(QnaItem(R.drawable.herb_basic_qna_icon,"안녕하세요. 얼마전 *을 선물로 받았습니다.\n어디에 좋은지 알 수 있을까요?","3"))
            add(QnaItem(R.drawable.herb_basic_qna_icon,"안녕하세요. 얼마전 *을 선물로 받았습니다.\n어디에 좋은지 알 수 있을까요?","5"))
            add(QnaItem(R.drawable.herb_basic_qna_icon,"안녕하세요. 얼마전 ***을 선물로 받았습니다.\n어디에 좋은지 알 수 있을까요?","3"))


            free_board_adapter.datas = free_board_datas
            free_board_adapter.notifyDataSetChanged()
        }




    }
}