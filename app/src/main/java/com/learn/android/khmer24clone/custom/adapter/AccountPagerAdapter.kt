package com.learn.android.khmer24clone.custom.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.learn.android.khmer24clone.ui.account.MyAdsFragment
import com.learn.android.khmer24clone.ui.account.MyFavFragment

class AccountPagerAdapter(
    fragmentManger: FragmentManager
) : FragmentStatePagerAdapter(fragmentManger, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            1 -> MyFavFragment()
            else -> MyAdsFragment()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return when (position) {
            1 -> "Favorites"
            else -> "Ads"
        }
    }

    override fun getCount(): Int {
        return 2
    }
}