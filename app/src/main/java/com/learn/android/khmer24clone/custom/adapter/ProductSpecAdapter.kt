package com.learn.android.khmer24clone.custom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learn.android.khmer24clone.R
import com.learn.android.khmer24clone.model.entity.ProductSpec
import kotlinx.android.synthetic.main.viewholder_product_spec.view.*

class ProductSpecAdapter: RecyclerView.Adapter<ProductSpecAdapter.ViewHolder>() {

    var dataList: ArrayList<ProductSpec> = arrayListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.viewholder_product_spec, parent, false)
        )
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(dataList[position])
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bindView(data: ProductSpec) {
            itemView.txtSpecKey.text = data.key ?: ""
            itemView.txtSpecValue.text = data.value ?: ""
        }
    }
}