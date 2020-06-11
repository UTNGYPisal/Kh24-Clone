package com.learn.android.khmer24clone.custom.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learn.android.khmer24clone.R
import com.learn.android.khmer24clone.common.extension.readableRelativeDate
import com.learn.android.khmer24clone.model.entity.Notification
import kotlinx.android.synthetic.main.viewholder_notification.view.*

class NotificationAdapter: RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    var dataList: ArrayList<Notification> = arrayListOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.viewholder_notification, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(dataList[position])
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bindView(data: Notification) {
            itemView.txtTitle.text = data.title ?: ""
            itemView.txtDate.text = data.createdAt?.readableRelativeDate ?: ""
        }
    }
}