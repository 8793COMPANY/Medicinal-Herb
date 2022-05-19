package com.corporation8793.medicinal_herb.activity.main

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.PaintDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.corporation8793.medicinal_herb.dto.ActionBar
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.TestDialog
import com.corporation8793.medicinal_herb.adapter.QnaAdapter
import com.corporation8793.medicinal_herb.databinding.ActivityChitchatBinding
import com.corporation8793.medicinal_herb.dialog.ChitChatDialog
import com.corporation8793.medicinal_herb.dto.HerbItem
import com.corporation8793.medicinal_herb.dto.QnaItem
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        binding.actionBar.backHome.setOnClickListener {
            finish()
        }

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


        binding.chitchatSelectBtn.setOnClickListener{
//            showDialog("hi")
            val dialog = TestDialog(this)
            dialog.showPopup()
        }

        val one_posting : Call<List<Post>> = RestClient.boardService.retrievePostInCategories("100","1","desc", RestClient.CATEGORY_CHITCHAT)

        one_posting.enqueue(object : Callback<List<Post>> {
            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
                val check : List<Post>? = response.body()
                var repo =""


                check?.forEach{ it->

                    Log.e("it","$it\n")

                }

            }

            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                Log.e("t",t.message.toString())
            }


        })

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

    private fun showDialog(title: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.activity_event)
        dialog.show()

    }
}