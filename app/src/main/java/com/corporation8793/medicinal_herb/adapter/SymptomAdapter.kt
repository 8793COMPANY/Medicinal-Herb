package com.corporation8793.medicinal_herb.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView
import com.corporation8793.medicinal_herb.R

class SymptomAdapter (private val dataSet : Array<String>, val height : Int) : RecyclerView.Adapter<SymptomAdapter.ViewHolder>() {

    interface OnItemClickListener{
        fun onItemClick(v:View, pos : Int)
    }
    var listener : OnItemClickListener? = null
     var number = 0
    fun setOnItemClickListener(listener : OnItemClickListener) {
        this.listener = listener
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val button: Button

        init {
            // Define click listener for the ViewHolder's View.
            button = view.findViewById(R.id.symptom)







        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.symptom_list_itemview, viewGroup, false)

        view.layoutParams.height = height

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element

        viewHolder.button.text = dataSet[position]
        if (position == number){
            viewHolder.button.isSelected = true
            viewHolder.button.setTextColor(Color.parseColor("#006837"))
        }else{
            viewHolder.button.isSelected = false
            viewHolder.button.setTextColor(Color.parseColor("#ffffff"))
        }





            viewHolder.button.setOnClickListener {
                listener?.onItemClick(viewHolder.button, position)

            }



    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSet.size

    fun getNumber(number : Int){
        this.number = number
    }




}