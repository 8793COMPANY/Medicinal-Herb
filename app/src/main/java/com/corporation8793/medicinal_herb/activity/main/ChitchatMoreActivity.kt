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
import com.corporation8793.medicinal_herb.Common
import com.corporation8793.medicinal_herb.dto.ActionBar
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.adapter.QnaAdapter
import com.corporation8793.medicinal_herb.databinding.ActivityChitchatBinding
import com.corporation8793.medicinal_herb.databinding.ActivityChitchatMoreBinding
import com.corporation8793.medicinal_herb.decoration.FarmDecoration
import com.corporation8793.medicinal_herb.dto.HerbItem
import com.corporation8793.medicinal_herb.dto.QnaItem
import com.corporation8793.medicinal_herb.herb_wp.rest.RestClient
import com.corporation8793.medicinal_herb.herb_wp.rest.data.board.Post
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChitchatMoreActivity : AppCompatActivity() {
    lateinit var binding : ActivityChitchatMoreBinding

    lateinit var qna_adapter : QnaAdapter
    lateinit var free_board_adapter : QnaAdapter

    val qna_datas = mutableListOf<QnaItem>()
    val free_board_datas = mutableListOf<QnaItem>()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    fun init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_chitchat_more)
        binding.setActionBar(ActionBar(intent.getStringExtra("title"), R.color.deep_green))
        var category = if(intent.getStringExtra("title")!!.equals("묻고 답하기")) RestClient.CATEGORY_QNA else RestClient.CATEGORY_CHITCHAT

        binding.actionBar.backHome.setOnClickListener {
            finish()
        }

        val display : DisplayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(display)
        val height : Int =  (display.heightPixels / 8.5).toInt()


        qna_adapter = QnaAdapter(this,height)
        free_board_adapter = QnaAdapter(this,height)

        binding.qnaList.adapter = qna_adapter


        val divider = FarmDecoration(1,resources.getColor(R.color.soft_gray))

        binding.qnaList.addItemDecoration(divider)




        val lm = LinearLayoutManager(this)

        binding.qnaList.layoutManager = lm



        GlobalScope.launch(Dispatchers.Default) {

//            val qna_posting : Call<List<Post>> = RestClient.boardService.retrievePostInCategories("100","1","desc", RestClient.CATEGORY_QNA)
            val check: List<Post>? = RestClient.boardService.retrievePostInCategories("100", "1", "asc", category).execute().body()
            Log.e("check", check!!.size.toString())
            qna_datas.apply {
                check.forEach {
                    var response = it.featured_media
                    if (!response.equals("0")){
                        response = RestClient.boardService.retrieveMedia(it.featured_media).execute().body()!!.guid.rendered
                    }

                        Log.e("id", it.id)
                        Log.e("id", it.content.rendered)
//                        Log.e("response", response.guid.rendered)

                        add(QnaItem(it.id,response, Common().replaceText(it.title.rendered),Common().replaceText(it.content.rendered),"3"))


                }
                qna_adapter.datas = qna_datas
                GlobalScope.launch(Dispatchers.Main) {    // 2
                    qna_adapter.notifyDataSetChanged()
                }


            }


        }


//        val qna_posting : Call<List<Post>> = RestClient.boardService.retrievePostInCategories("100","1","desc", RestClient.CATEGORY_QNA)
//
//        qna_posting.enqueue(object : Callback<List<Post>> {
//            override fun onResponse(call: Call<List<Post>>, response: Response<List<Post>>) {
//                val check : List<Post>? = response.body()
//                var repo =""
//
//                qna_datas.apply {
//                    check?.forEach{ it->
//
//                        Log.e("it","$it\n")
//
//                        add(QnaItem(it.id,it.featured_media,replaceWord(it.title.rendered),replaceWord(it.content.rendered),"3"))
//                    }
//
//
//
//                    qna_adapter.datas = qna_datas
//                    qna_adapter.notifyDataSetChanged()
//                }
//
//
//            }
//
//            override fun onFailure(call: Call<List<Post>>, t: Throwable) {
//                Log.e("t",t.message.toString())
//            }
//
//
//        })



//        qna_datas.apply {
//            add(QnaItem(R.drawable.herb_basic_qna_icon,"안녕하세요. 얼마전 ***을 선물로 받았습니다.\n어디에 좋은지 알 수 있을까요?","1"))
//            add(QnaItem(R.drawable.herb_basic_qna_icon,"안녕하세요. 얼마전 *을 선물로 받았습니다.\n어디에 좋은지 알 수 있을까요?","2"))
//            add(QnaItem(R.drawable.herb_basic_qna_icon,"안녕하세요. 얼마전 **을 선물로 받았습니다.\n어디에 좋은지 알 수 있을까요?","3"))
//
//            qna_adapter.datas = qna_datas
//            qna_adapter.notifyDataSetChanged()
//        }







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