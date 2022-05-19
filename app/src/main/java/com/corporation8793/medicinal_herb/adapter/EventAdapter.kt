package com.corporation8793.medicinal_herb.adapter

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.medicinal_herb.R
import com.corporation8793.medicinal_herb.dto.EventItem
import com.corporation8793.medicinal_herb.dto.HerbItem
import com.corporation8793.medicinal_herb.fragment.EventDetailFragment

class EventAdapter (private val context: Context?, val height : Int) : RecyclerView.Adapter<EventAdapter.ViewHolder>() {
    var datas = mutableListOf<EventItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.event_list_itemview,parent,false)
        view.layoutParams.height = height
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
        Log.e("in datas",datas[position].date)
    }

    override fun getItemCount(): Int  = datas.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val eventImg: ImageView = itemView.findViewById(R.id.herb_event_img)
        private val date: TextView = itemView.findViewById(R.id.event_end_date)


        fun bind(item: EventItem) {
            date.text = "당첨자 발표 " + item.date
            if (item.img == 0)
                eventImg.setBackgroundResource(R.drawable.round_shape_background)
            else
                eventImg.setBackgroundResource(R.drawable.green_box)

            itemView.setOnClickListener{
                val bundle : Bundle = Bundle()
                bundle.putString("id",item.id)
                var frag = EventDetailFragment()
                frag.arguments = bundle
                (it.context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.fragment_container,frag).addToBackStack(null).commit()
            }


//            Glide.with(itemView).load(item.img).into(imgProfile)

        }
    }

}