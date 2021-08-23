package com.paparazziteam.marketshop.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.paparazziteam.marketshop.Models.Product
import com.paparazziteam.marketshop.R


class ProductAdapter(private val producList: ArrayList<Product>) : RecyclerView.Adapter<ProductAdapter.viewHolder>() {

    //var producList = producList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.cardview_product,parent,false)

        return viewHolder(itemview)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        val currentItem = producList[position]

        holder.name.text = currentItem.name //set name

    }

    override fun getItemCount(): Int {

        return producList.size
    }


    class viewHolder(itemview : View): RecyclerView.ViewHolder(itemview) {

        val name: TextView = itemview.findViewById(R.id.product_name)

    }


}