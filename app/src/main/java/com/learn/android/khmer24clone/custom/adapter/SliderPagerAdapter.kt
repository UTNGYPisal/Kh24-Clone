package com.learn.android.khmer24clone.custom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learn.android.khmer24clone.GlideApp
import com.learn.android.khmer24clone.R
import com.learn.android.khmer24clone.model.entity.Slide
import kotlinx.android.synthetic.main.viewholder_image_slider.view.*


class SliderPagerAdapter : RecyclerView.Adapter<SliderPagerAdapter.SliderViewHolder>() {

    var dataList: ArrayList<Slide> = arrayListOf()
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

        fun bindView(data: Slide) {
            GlideApp.with(itemView.context).load(data.url ?: "").into(itemView.imageView)
        }
    }
}