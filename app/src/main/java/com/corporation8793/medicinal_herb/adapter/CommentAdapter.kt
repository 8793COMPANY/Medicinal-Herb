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
import com.corporation8793.medicinal_herb.Common
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.dto.CommentItem
import com.corporation8793.medicinal_herb.dto.EventItem
import com.corporation8793.medicinal_herb.dto.HerbItem
import com.corporation8793.medicinal_herb.fragment.EventDetailFragment

class CommentAdapter (private val context: Context?, val height : Int) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var datas = mutableListOf<CommentItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        val view : View ?

        return when(viewType){
            0->{
                view = LayoutInflater.from(context).inflate(R.layout.comment_list_itemview,parent,false)
                view!!.layoutParams.height = height
                CommentViewHolder(view)
            }
            else->{
                view = LayoutInflater.from(context).inflate(R.layout.reply_list_itemview,parent,false)
                view!!.layoutParams.height = height
                ReplyViewHolder(view)
            }


        }


    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(datas[position].type) {
            0 -> {
                (holder as CommentViewHolder).bind(datas[position])
                holder.setIsRecyclable(false)
            }
            else -> {
                (holder as ReplyViewHolder).bind(datas[position])
                holder.setIsRecyclable(false)
            }

        }
        Log.e("in datas",datas[position].date)
    }

    override fun getItemCount(): Int  = datas.size

    override fun getItemViewType(position: Int): Int {

        return datas[position].type
    }

    inner class CommentViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val eventImg: ImageView = itemView.findViewById(R.id.comment_user_img)
        private val date: TextView = itemView.findViewById(R.id.comment_date)
        private val comment_user_name : TextView = itemView.findViewById(R.id.comment_user_name)
        private val comment_text : TextView = itemView.findViewById(R.id.comment_text)


        fun bind(item: CommentItem) {
            date.text = item.date.replace("T"," ").replace("-",".")
            if (item.img == 0)
                eventImg.setBackgroundResource(R.drawable.herb_basic_qna_icon)
            else
                eventImg.setBackgroundResource(R.drawable.green_box)

            comment_user_name.text = item.user_name
            comment_text.text =  Common().replaceText(item.comment)


//            Glide.with(itemView).load(item.img).into(imgProfile)

        }
    }

    inner class ReplyViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val eventImg: ImageView = itemView.findViewById(R.id.comment_user_img)
        private val date: TextView = itemView.findViewById(R.id.comment_date)
        private val comment_user_name : TextView = itemView.findViewById(R.id.comment_user_name)
        private val comment_text : TextView = itemView.findViewById(R.id.comment_text)


        fun bind(item: CommentItem) {
            date.text = item.date.replace("T"," ").replace("-",".")
            if (item.img == 0)
                eventImg.setBackgroundResource(R.drawable.herb_basic_qna_icon)
            else
                eventImg.setBackgroundResource(R.drawable.green_box)

            comment_user_name.text = item.user_name
            comment_text.text = Common().replaceText(item.comment)



//            Glide.with(itemView).load(item.img).into(imgProfile)

        }
    }



}