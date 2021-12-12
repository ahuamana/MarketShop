package com.paparazziteam.marketshop.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.paparazziteam.marketshop.Models.Product
import com.paparazziteam.marketshop.R
import com.paparazziteam.marketshop.Utils.StaticUtil
import com.paparazziteam.marketshop.databinding.CardviewProductBinding


class ProductAdapter(producList: ArrayList<Product>, context:Context) : RecyclerView.Adapter<ProductAdapter.viewHolder>() {

    var producList = producList
    var context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val itemview = LayoutInflater.from(parent.context).inflate(R.layout.cardview_product,parent,false)

        return viewHolder(itemview)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {

        val currentItem = producList[position]
        //holder.name.text = currentItem.name //set name
        holder.bind(currentItem, context)
    }

    override fun getItemCount(): Int {

        return producList.size
    }


    class viewHolder(itemview : View): RecyclerView.ViewHolder(itemview) {

        //val name: TextView = itemview.findViewById(R.id.product_name)
        val binding = CardviewProductBinding.bind(itemview)

        fun bind(item:Product, context: Context)
        {
            var new =StaticUtil.replaceFirstCharInSequenceToUppercase(item.name)
            binding.productName.text = new
            binding.productBarcode.text = item.barcode
            binding.productPrice.text = item.precioUnitario.toString()

            if(item.photo != null)
            {
                Glide.with(context)
                    .load(item.photo)
                    .placeholder(R.drawable.ic_launcher_background)
                    .into(binding.productPhoto)
            }

        }

    }


}