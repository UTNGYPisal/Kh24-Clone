package com.learn.android.khmer24clone.custom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learn.android.khmer24clone.BuildConfig
import com.learn.android.khmer24clone.GlideApp
import com.learn.android.khmer24clone.R
import com.learn.android.khmer24clone.common.extension.readableRelativeDate
import com.learn.android.khmer24clone.custom.helper.ItemClickListener
import com.learn.android.khmer24clone.model.entity.Product
import kotlinx.android.synthetic.main.viewholder_product_list.view.*

class ProductSearchAdapter: RecyclerView.Adapter<ProductSearchAdapter.ProductListViewHolder>() {

    var itemClickListener: ItemClickListener?  = null

    var dataList: ArrayList<Product> = arrayListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        return ProductListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.viewholder_product_search, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bindView(dataList[position])
    }

    inner class ProductListViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bindView(data: Product){
            itemView.txtTitle.text = data.title ?: ""
            itemView.setOnClickListener {
                itemClickListener?.invoke(adapterPosition, data)
            }
        }
    }
}