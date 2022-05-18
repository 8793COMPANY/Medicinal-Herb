package com.corporation8793.medicinal_herb

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater

class TestDialog (context: Context) : Dialog(context) {

    init {
        dialog = Dialog(context)
    }

    fun showPopup() {

        val dialogview = LayoutInflater.from(context)
                .inflate(R.layout.dialog_photo_select, null, false)
        //initializing dialog screen

        dialog?.setCancelable(true)
        dialog?.setContentView(dialogview)
        dialog?.show()

    }
    companion object{
        var dialog: Dialog? = null
        fun dismissPopup() = dialog?.let { dialog!!.dismiss() }
    }

}