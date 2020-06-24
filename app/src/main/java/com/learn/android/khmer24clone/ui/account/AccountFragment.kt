package com.learn.android.khmer24clone.ui.account

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.learn.android.khmer24clone.GlideApp
import com.learn.android.khmer24clone.R
import com.learn.android.khmer24clone.custom.adapter.AccountPagerAdapter
import com.learn.android.khmer24clone.model.entity.User
import com.learn.android.khmer24clone.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_account.*

class AccountFragment : BaseFragment(R.layout.fragment_account) {

    private lateinit var viewPagerAdapter: AccountPagerAdapter

    //**************************************************************
    //region Lifecycle
    //**************************************************************
    override fun onResume() {
        super.onResume()
        if (!User.isLoggedIn) {
            findNavController().navigate(R.id.loginFragment)
            return
        }

        requireActivity().run {
            this.toolbar.isVisible = true
            this.navView.isVisible = true
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initData()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.account_option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
    //endregion

    //**************************************************************
    //region Initialization
    //**************************************************************
    private fun initUI(){
        setHasOptionsMenu(true)
        viewPagerAdapter = AccountPagerAdapter(childFragmentManager)
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    private fun initData(){
        if (User.current == null) {
            findNavController().navigateUp()
            return
        }
        updateProfileUI(User.current!!)
    }

    private fun updateProfileUI(user: User){
        txtName.text = user.name ?: ""
        GlideApp.with(imgAvatar).load(user.avatar).into(imgAvatar)
    }
    //endregion
}