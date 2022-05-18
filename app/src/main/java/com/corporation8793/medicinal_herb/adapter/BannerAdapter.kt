package com.corporation8793.medicinal_herb.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.dto.EventItem
import com.corporation8793.medicinal_herb.dto.HerbItem

class BannerAdapter (private val context: Context, private val images : Array<Int>) : RecyclerView.Adapter<BannerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.banner_itemview,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(images.get(position))

    }

    override fun getItemCount(): Int  = images.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val bannerImg: ImageView = itemView.findViewById(R.id.banner_item)


        fun bind(item: Int) {

            Glide.with(context)
                    .load(item)
                    .into(bannerImg);


//            Glide.with(itemView).load(item.img).into(imgProfile)

        }
    }

}