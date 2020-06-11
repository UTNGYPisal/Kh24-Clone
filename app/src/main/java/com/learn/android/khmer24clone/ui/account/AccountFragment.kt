package com.learn.android.khmer24clone.ui.account

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.learn.android.khmer24clone.R
import com.learn.android.khmer24clone.ui.base.BaseFragment

class AccountFragment : BaseFragment(R.layout.fragment_account) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findNavController().navigate(R.id.loginFragment)
    }
}