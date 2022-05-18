package com.corporation8793.medicinal_herb.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.dto.FarmItem
import com.corporation8793.medicinal_herb.dto.HerbItem

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
        private val txtName: TextView = itemView.findViewById(R.id.farm_name)


        fun bind(item: FarmItem) {
            txtName.text = item.farm_name
//            Glide.with(itemView).load(item.img).into(imgProfile)

        }
    }

}