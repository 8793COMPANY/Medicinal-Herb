package com.corporation8793.medicinal_herb.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import com.corporation8793.medicinal_herb.R

class RecommendAdapter(val context : Context,val buttonNames : Array<String>) : BaseAdapter() {
    val btn_id = 0
    val total_btns = 20
    override fun getCount(): Int {
        return buttonNames.size
    }

    override fun getItem(p0: Int): Any {
        return buttonNames.get(p0)
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val button : Button
        if (p1 != null){
            button = p1 as Button
        }else{
            button = Button(context)
            button.setText(buttonNames[p0])
            button.setBackgroundResource(R.drawable.button_click_selector)
            button.setTag(buttonNames[p0])
//            button.setOnClickListener{
//                if (button.isSelected){
//                    button.isSelected = false
//                    button.setTextColor(context.resources.getColor(R.color.white))
//                }else{
//                    button.isSelected = true
//                    button.setTextColor(context.resources.getColor(R.color.green))
//                }
//            }
        }

        return button
    }
}