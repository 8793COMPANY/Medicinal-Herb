package com.corporation8793.medicinal_herb.activity.main

import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.PaintDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import com.corporation8793.medicinal_herb.dto.ActionBar
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.databinding.ActivityQnaBinding

class QnaActivity : AppCompatActivity() {
    lateinit var binding : ActivityQnaBinding
    lateinit var dialog:Dialog
    lateinit var register_dialog:Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()

        binding.qnaRegistrationBtn.setOnClickListener {
            showOkDialog()
            Log.e("qna","click")
        }

        binding.photoRegistration.setOnClickListener {
            showOkDialog()
        }



    }

    fun init(){
        binding = DataBindingUtil.setContentView(this, R.layout.activity_qna)
        binding.setActionBar(ActionBar("묻고 답하기", R.color.black))
    }

    fun showDialog(){
        val layoutInflater = LayoutInflater.from(this)
        val view = layoutInflater.inflate(R.layout.dialog_photo_select, null)




        val alertDialog = AlertDialog.Builder(this)
                .setView(view).create()



        alertDialog.show()

        val display = windowManager.defaultDisplay
        val size = Point()
        display.getSize(size)


        val mLayoutParams = WindowManager.LayoutParams()
        mLayoutParams.width = (size.x * 0.7f).toInt()
        mLayoutParams.height = (size.y * 0.4f).toInt()

        alertDialog.window?.attributes = mLayoutParams

    }

    fun showOkDialog(){
        val layoutInflater = LayoutInflater.from(this)
//        val view = layoutInflater.inflate(R.layout.dialog_posting_check, null)
//
//
//
//        val alertDialog = AlertDialog.Builder(this)
//                .setView(view).create()


        val dlgView = View.inflate(this, R.layout.dialog_photo_select, null)
        val dlg = AlertDialog.Builder(this).create()
        dlg.setView(dlgView)
        dlg.window?.setBackgroundDrawable(PaintDrawable(Color.WHITE))
        dlg.show()


//        val display = windowManager.defaultDisplay
//        val size = Point()
//        display.getSize(size)
//
//        // Generate custom width and height and
//        // add to the dialog attributes
//        // we multiplied the width and height by 0.5,
//        // meaning reducing the size to 50%
//        val mLayoutParams = WindowManager.LayoutParams()
//        mLayoutParams.width = (size.x * 0.7f).toInt()
//        mLayoutParams.height = (size.y * 0.3f).toInt()
//
//        dlg.window?.attributes = mLayoutParams


    }

    fun dialogTest(){
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.dialog_chitchat_select)
        dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
        dialog.setCanceledOnTouchOutside(true)
        dialog.setCancelable(true)
        dialog.show()

    }
}