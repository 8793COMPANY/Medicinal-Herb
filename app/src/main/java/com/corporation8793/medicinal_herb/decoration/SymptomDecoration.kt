package com.corporation8793.medicinal_herb.decoration

import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SymptomDecoration(
        private val height: Int)
    : RecyclerView.ItemDecoration() {




    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val index : Int = ((view.layoutParams) as GridLayoutManager.LayoutParams).spanIndex
        Log.e("span index", index.toString())

//        if(parent.getChildAdapterPosition(view)==0){
//            outRect.top = height
//        }
//
//        outRect.bottom = height;

            outRect.bottom = 15
        if (parent.getChildLayoutPosition(view) % 3 !=2){
            outRect.right = 15
        }


    }


}