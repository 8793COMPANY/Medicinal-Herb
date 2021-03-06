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
import com.corporation8793.medicinal_herb.Common
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.activity.main.FarmDetailActivity
import com.corporation8793.medicinal_herb.dto.FarmItem

class FarmAdapter (private val context: Context, val height : Int) : RecyclerView.Adapter<FarmAdapter.ViewHolder>() {
    var datas = mutableListOf<FarmItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.farm_list_itemview,parent,false)
        view.layoutParams.height = height
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
        Log.e("in datas",datas[position].farm_name)
    }

    override fun getItemCount(): Int  = datas.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imgProfile: ImageView = itemView.findViewById(R.id.farm_user_img)
        private val farmName: TextView = itemView.findViewById(R.id.farm_name)
        private val userName: TextView = itemView.findViewById(R.id.farm_user_name)
        private val farm_Introduction: TextView = itemView.findViewById(R.id.farm_Introduction)
        private val farm_comment_count : TextView = itemView.findViewById(R.id.farm_comment_count)


        fun bind(item: FarmItem) {
            farmName.text = item.farm_name
            userName.text = item.farm_user_name
            farm_Introduction.text = Common().replaceText(item.introduction)
            farm_comment_count.text = item.comment_count.toString()
            itemView.setOnClickListener{
                var intent : Intent = Intent(context, FarmDetailActivity::class.java)
                intent.putExtra("farm_name",item.farm_name)
                intent.putExtra("id",item.id)
                intent.putExtra("farm_img",item.farm_img)
                context.startActivity(intent)
            }
//            Glide.with(itemView).load(item.img).into(imgProfile)

        }
    }

}