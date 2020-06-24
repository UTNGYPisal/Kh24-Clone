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

class ProductAdapter: RecyclerView.Adapter<ProductAdapter.ProductListViewHolder>() {

    var itemClickListener: ItemClickListener?  = null
    var favClickListener: ItemClickListener?  = null

    var dataList: ArrayList<Product> = arrayListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount() = dataList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductListViewHolder {
        return ProductListViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.viewholder_product_list, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ProductListViewHolder, position: Int) {
        holder.bindView(dataList[position])
    }

    inner class ProductListViewHolder(view: View): RecyclerView.ViewHolder(view){
        fun bindView(data: Product){
            GlideApp.with(itemView)
                .load(BuildConfig.BASE_URL + "/storage/" + data.thumbnail)
                .into(itemView.imgThumbnail)

            itemView.txtTitle.text = data.title ?: ""
            itemView.txtLocation.text = data.province?.name ?: ""
            itemView.txtTime.text = data.createdAt?.readableRelativeDate ?: ""
            itemView.txtPrice.text = "USD ${data.price ?: 0.00}"

            itemView.setOnClickListener {
                itemClickListener?.invoke(adapterPosition, data)
            }
            itemView.iBtnFav.setOnClickListener {
                favClickListener?.invoke(adapterPosition, data)
            }
        }
    }
}