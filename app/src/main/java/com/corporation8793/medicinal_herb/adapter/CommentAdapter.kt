package com.corporation8793.medicinal_herb.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.dto.CommentItem
import com.corporation8793.medicinal_herb.dto.EventItem
import com.corporation8793.medicinal_herb.dto.HerbItem
import com.corporation8793.medicinal_herb.fragment.EventDetailFragment

class CommentAdapter (private val context: Context?, val height : Int) : RecyclerView.Adapter<CommentAdapter.ViewHolder>() {
    var datas = mutableListOf<CommentItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.comment_list_itemview,parent,false)
        view.layoutParams.height = height
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
        Log.e("in datas",datas[position].date)
    }

    override fun getItemCount(): Int  = datas.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val eventImg: ImageView = itemView.findViewById(R.id.comment_user_img)
        private val date: TextView = itemView.findViewById(R.id.comment_date)


        fun bind(item: CommentItem) {
            date.text = item.date
            if (item.img == 0)
                eventImg.setBackgroundResource(R.drawable.herb_basic_qna_icon)
            else
                eventImg.setBackgroundResource(R.drawable.green_box)




//            Glide.with(itemView).load(item.img).into(imgProfile)

        }
    }

}