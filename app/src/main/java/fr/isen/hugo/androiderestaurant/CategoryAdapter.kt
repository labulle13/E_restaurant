package fr.isen.hugo.androiderestaurant

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

internal class CategoryAdapter(private var arrayListOf: ArrayList<String>, val clickListener: (String) -> Unit) : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemTextView: TextView = view.findViewById(R.id.categoryTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_category, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.i("XXX", "onBindViewHolder")
        val item = arrayListOf[position]
        holder.itemTextView.text = item

        holder.itemView.setOnClickListener {
            clickListener(item)
        }
        }


        override fun getItemCount(): Int {
            return arrayListOf.size
        }
    }
