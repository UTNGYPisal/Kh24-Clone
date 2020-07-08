package com.learn.android.khmer24clone.custom.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.learn.android.khmer24clone.ui.account.MyAdsFragment
import com.learn.android.khmer24clone.ui.account.MyFavFragment

class AccountPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            1 -> MyFavFragment()
            else -> MyAdsFragment()
        }
    }
}