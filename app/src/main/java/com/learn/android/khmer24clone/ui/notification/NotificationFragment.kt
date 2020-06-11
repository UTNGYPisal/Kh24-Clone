package com.learn.android.khmer24clone.ui.notification

import android.os.Bundle
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.android.khmer24clone.R
import com.learn.android.khmer24clone.common.helper.printLog
import com.learn.android.khmer24clone.custom.adapter.NotificationAdapter
import com.learn.android.khmer24clone.model.api.UnhandledResult
import com.learn.android.khmer24clone.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_notification.*
import org.koin.android.ext.android.inject

class NotificationFragment : BaseFragment(R.layout.fragment_notification) {
    val viewModel by inject<NotificationViewModel>()
    lateinit var adapter: NotificationAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initData()
        initListener()
    }

    private fun initUI(){
        adapter = NotificationAdapter()
        rclNotifications.layoutManager = LinearLayoutManager(requireContext())
        rclNotifications.adapter = adapter
    }

    private fun initData(){
        viewModel.fetchNotifications().observe(viewLifecycleOwner, Observer {
            when (it) {
                UnhandledResult.Loading -> {
                    progressBar.isVisible = !swipeRefreshLayout.isRefreshing
                }
                is UnhandledResult.Error -> {
                    progressBar.isGone = true
                    printLog("Error: ${it.exception}")
                }
                is UnhandledResult.Success -> {
                    progressBar.isGone = true
                    swipeRefreshLayout.isRefreshing = false
                    adapter.dataList = it.data?.result ?: arrayListOf()
                }
            }
        })
    }

    private fun initListener() {
        swipeRefreshLayout.setOnRefreshListener {
            adapter.dataList = arrayListOf()
            initData()
        }
    }
}