package com.learn.android.khmer24clone.custom.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.learn.android.khmer24clone.R
import kotlinx.android.synthetic.main.view_single_indicator.view.*

class SingleIndicatorView @JvmOverloads
constructor(context: Context, attributeSet: AttributeSet? = null, defAttr: Int = 0)
    : LinearLayout(context, attributeSet, defAttr){

    override fun isSelected(): Boolean {
        return cardView.isSelected
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        cardView.isSelected = selected
    }

    init {
        View.inflate(context, R.layout.view_single_indicator, this)
    }
}