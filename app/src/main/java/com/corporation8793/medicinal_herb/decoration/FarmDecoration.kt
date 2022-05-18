package com.corporation8793.medicinal_herb.decoration

import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class FarmDecoration(
        private val height: Int,
        private val color: Int)
    : RecyclerView.ItemDecoration() {

    private val paint = Paint()
    init {
        paint.color = color
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = (parent.paddingStart).toFloat()
        val right = (parent.width - parent.paddingEnd).toFloat()
        for (i in 0 until parent.childCount) {
            // bottom divider draw
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            var top = (child.bottom + params.bottomMargin).toFloat()
            val bottom = top + height

            c.drawRect(left, top, right, bottom, paint)

            if (i == 0){
                // top divider draw : only first item
                var top = (child.top + params.topMargin).toFloat()
                val bottom = top + height

                c.drawRect(left, top, right, bottom, paint)
            }

        }
    }


//    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
//        super.getItemOffsets(outRect, view, parent, state)
//        if(parent.getChildAdapterPosition(view)==0){
//            outRect.top = height
//        }
//
//        outRect.bottom = height;
//
//    }


}