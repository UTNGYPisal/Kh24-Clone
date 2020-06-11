package com.learn.android.khmer24clone.custom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learn.android.khmer24clone.BuildConfig
import com.learn.android.khmer24clone.GlideApp
import com.learn.android.khmer24clone.R
import com.learn.android.khmer24clone.custom.helper.ItemClickListener
import com.learn.android.khmer24clone.model.entity.Category
import kotlinx.android.synthetic.main.viewholder_category.view.*

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    var itemClickListener: ItemClickListener?  = null

    var dataList: ArrayList<Category> = arrayListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.viewholder_category, parent, false)
        )
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bindView(dataList[position])
    }


    //**************************************************************
    //region ViewHolder
    //**************************************************************
    inner class CategoryViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bindView(data: Category){
            GlideApp.with(itemView).load(BuildConfig.BASE_URL + data.icon).into(itemView.imgCategory)
            itemView.txtTitle.text = data.name

            itemView.setOnClickListener {
                itemClickListener?.invoke(adapterPosition, data)
            }
        }
    }
    //endregion
}