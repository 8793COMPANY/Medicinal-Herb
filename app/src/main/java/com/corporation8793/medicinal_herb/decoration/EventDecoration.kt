package com.corporation8793.medicinal_herb.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class EventDecoration(
        private val height: Int)
    : RecyclerView.ItemDecoration() {




    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)



//        if(parent.getChildAdapterPosition(view)==0){
//            outRect.top = height
//        }
//
//        outRect.bottom = height;

            outRect.bottom = 25



    }


}