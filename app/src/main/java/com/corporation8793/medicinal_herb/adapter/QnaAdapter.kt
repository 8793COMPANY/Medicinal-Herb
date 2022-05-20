package com.corporation8793.medicinal_herb.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.activity.main.ChitchatDetailActivity
import com.corporation8793.medicinal_herb.activity.main.MainActivity2
import com.corporation8793.medicinal_herb.dto.HerbItem
import com.corporation8793.medicinal_herb.dto.QnaItem

class QnaAdapter (private val context: Context, val height : Int) : RecyclerView.Adapter<QnaAdapter.ViewHolder>() {
    var datas = mutableListOf<QnaItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.qna_list_itemview,parent,false)
        view.layoutParams.height = height
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
        Log.e("in datas",datas[position].comment)
    }

    override fun getItemCount(): Int  = datas.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imgProfile: ImageView = itemView.findViewById(R.id.herb_qna_img)
        private val txtName: TextView = itemView.findViewById(R.id.herb_qna_text)


        fun bind(item: QnaItem) {
            txtName.text = item.question+"\n댓글:"+item.comment+"개"
//            Glide.with(itemView).load(item.img).into(imgProfile)

            itemView.setOnClickListener{
                var intent : Intent = Intent(context, ChitchatDetailActivity::class.java)
                context.startActivity(intent)
            }

        }
    }

}