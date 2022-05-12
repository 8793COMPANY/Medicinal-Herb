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
import com.corporation8793.medicinal_herb.activity.main.DictionaryActivity
import com.corporation8793.medicinal_herb.activity.main.HerbDetailActivity
import com.corporation8793.medicinal_herb.dto.HerbItem

class HerbAdapter (private val context: Context, val height : Int) : RecyclerView.Adapter<HerbAdapter.ViewHolder>() {
    var datas = mutableListOf<HerbItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.herb_list_itemview,parent,false)
        view.layoutParams.height = height
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
        Log.e("in datas",datas[position].name)
    }

    override fun getItemCount(): Int  = datas.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val imgProfile: ImageView = itemView.findViewById(R.id.herb_img)
        private val txtName: TextView = itemView.findViewById(R.id.herb_name)




        fun bind(item: HerbItem) {
            txtName.text = item.name
            imgProfile.setBackgroundResource(R.drawable.green_box)
//            Glide.with(itemView).load(item.img).into(imgProfile)

            itemView.setOnClickListener{
                var intent : Intent = Intent(context, HerbDetailActivity::class.java)
                intent.putExtra("name",item.name)
                context.startActivity(intent)
            }

        }


    }

}