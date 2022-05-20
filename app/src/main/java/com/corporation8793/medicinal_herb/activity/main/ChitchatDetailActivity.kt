package com.corporation8793.medicinal_herb.activity.main

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout.VERTICAL
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.corporation8793.medicinal_herb.dto.ActionBar
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.activity.join.JoinActivity
import com.corporation8793.medicinal_herb.adapter.QnaAdapter
import com.corporation8793.medicinal_herb.databinding.ActivityChitchatBinding
import com.corporation8793.medicinal_herb.databinding.ActivityChitchatDetailBinding
import com.corporation8793.medicinal_herb.databinding.ActivityFarmDetailBinding
import com.corporation8793.medicinal_herb.decoration.FarmDecoration
import com.corporation8793.medicinal_herb.decoration.QnaDecoration
import com.corporation8793.medicinal_herb.dto.QnaItem
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Post
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChitchatDetailActivity : AppCompatActivity() {
    lateinit var binding : ActivityChitchatDetailBinding

    lateinit var qna_adapter : QnaAdapter
    lateinit var free_board_adapter : QnaAdapter

    val qna_datas = mutableListOf<QnaItem>()
    val free_board_datas = mutableListOf<QnaItem>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    fun init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chitchat_detail)
        binding.setActionBar(ActionBar("", R.color.deep_green))

        binding.actionBar.backHome.setOnClickListener {
            finish()
        }



//        qna_datas.apply {
//            add(QnaItem(R.drawable.herb_basic_qna_icon,"안녕하세요. 얼마전 ***을 선물로 받았습니다.\n어디에 좋은지 알 수 있을까요?","1"))
//            add(QnaItem(R.drawable.herb_basic_qna_icon,"안녕하세요. 얼마전 *을 선물로 받았습니다.\n어디에 좋은지 알 수 있을까요?","2"))
//            add(QnaItem(R.drawable.herb_basic_qna_icon,"안녕하세요. 얼마전 **을 선물로 받았습니다.\n어디에 좋은지 알 수 있을까요?","3"))
//
//            qna_adapter.datas = qna_datas
//            qna_adapter.notifyDataSetChanged()
//        }







    }

    fun replaceWord(text : String) : String{
        return text.replace("<p>","").replace("</p>","")
        .replace("<ul>","").replace("</ul>","")
                .replace("<li>","").replace("</li>","")
                .replace("<br>","").replace("<br />","")
                .replace("<strong>","").replace("</strong>","")

    }


    private fun showDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_chitchat_select)
        dialog.show()

//        dialog.findViewById<TextView>(R.id.qna).setOnClickListener{
//            dialog.dismiss()
//        }
//

        dialog.findViewById<ImageView>(R.id.cancel_btn).setOnClickListener{
            dialog.dismiss()
        }


        dialog.findViewById<ConstraintLayout>(R.id.qna_area).setOnClickListener{
            dialog.dismiss()
            var intent : Intent = Intent(this, QnaActivity::class.java)
            intent.putExtra("category","묻고 답하기")
            startActivity(intent)
        }

        dialog.findViewById<ConstraintLayout>(R.id.free_board_area).setOnClickListener{
            dialog.dismiss()
            var intent : Intent = Intent(this, QnaActivity::class.java)
            intent.putExtra("category","자유 게시판")
            startActivity(intent)
        }



        dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));

//        dialog.getWindow()!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

                val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)

        // Generate custom width and height and
        // add to the dialog attributes
        // we multiplied the width and height by 0.5,
        // meaning reducing the size to 50%
        val mLayoutParams = WindowManager.LayoutParams()
        mLayoutParams.width = (size.x * 0.57f).toInt()
        mLayoutParams.height = (size.y * 0.3f).toInt()

        dialog.window?.attributes = mLayoutParams

    }
}