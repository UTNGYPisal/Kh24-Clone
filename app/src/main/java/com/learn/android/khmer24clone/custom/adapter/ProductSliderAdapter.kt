package com.learn.android.khmer24clone.custom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learn.android.khmer24clone.BuildConfig
import com.learn.android.khmer24clone.GlideApp
import com.learn.android.khmer24clone.R
import com.learn.android.khmer24clone.model.entity.Slide
import kotlinx.android.synthetic.main.viewholder_image_slider.view.*


class ProductSliderAdapter : RecyclerView.Adapter<ProductSliderAdapter.SliderViewHolder>() {

    var dataList: ArrayList<String> = arrayListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderViewHolder {
        return SliderViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(R.layout.viewholder_image_slider, parent, false)
        )
    }

    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: SliderViewHolder, position: Int) {
        holder.bindView(dataList[position])
    }


    inner class SliderViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bindView(url: String) {
            GlideApp.with(itemView.context).load(BuildConfig.BASE_URL + "/storage/"  + url).into(itemView.imageView)
        }
    }
}