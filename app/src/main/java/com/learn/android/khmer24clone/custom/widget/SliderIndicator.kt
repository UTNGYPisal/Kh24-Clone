package com.learn.android.khmer24clone.custom.widget

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.viewpager2.widget.ViewPager2
import com.learn.android.khmer24clone.R
import java.lang.Exception
import java.util.*

class SliderIndicator
@JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defAttr: Int = 0):
        LinearLayout(context, attributeSet, defAttr) {

    var itemsCount: Int = 0
    set(value) {
        field = value
        updateLayout()
    }


    init {
        View.inflate(context, R.layout.view_slider_indicator, this)
    }

    fun updateLayout(){
        this.removeAllViews()
        for (i in 0 until itemsCount) {
            val singleIndicator = SingleIndicatorView(context)
            if (i == 0) {
                singleIndicator.isSelected = true
            }
            this.addView(singleIndicator)
        }
    }

    fun setupWithViewPager(viewPager2: ViewPager2) {
        Timer().scheduleAtFixedRate(object : TimerTask(){
            override fun run() {
                try {
                    Handler(Looper.getMainLooper()).post {
                        val currentItem = viewPager2.currentItem
                        viewPager2.setCurrentItem(if (currentItem == itemsCount - 1) 0 else currentItem + 1, true)
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }, 0, 3000)

        viewPager2.registerOnPageChangeCallback(object: ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                try {
                    children.forEach { it.isSelected = false }
                    getChildAt(position).isSelected = true
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        })
    }

}