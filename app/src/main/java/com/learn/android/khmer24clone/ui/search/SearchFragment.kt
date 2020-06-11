package com.learn.android.khmer24clone.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.android.khmer24clone.R
import com.learn.android.khmer24clone.common.helper.printLog
import com.learn.android.khmer24clone.common.helper.showKeyboard
import com.learn.android.khmer24clone.custom.adapter.ProductSearchAdapter
import com.learn.android.khmer24clone.model.api.UnhandledResult
import com.learn.android.khmer24clone.model.entity.Product
import com.learn.android.khmer24clone.ui.base.BaseFragment
import com.learn.android.khmer24clone.ui.product.detail.ProductDetailViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.koin.android.ext.android.inject

class SearchFragment : BaseFragment(R.layout.fragment_search) {
    private val viewModel by inject<SearchViewModel>()
    private lateinit var adapter: ProductSearchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initListener()
    }

    override fun onResume() {
        super.onResume()

        requireActivity().run {
            toolbar.isGone = true
            navView.isGone = true
        }

        showKeyboard(etSearch)
    }

    private fun initListener(){
        btnDismiss.setOnClickListener {
            findNavController().navigateUp()
        }

        etSearch.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                layoutNoResult.isGone = true
                adapter.dataList = arrayListOf()
                viewModel.searchProductsByKeyword(s.toString()).observe(viewLifecycleOwner, Observer {
                    when (it) {
                        UnhandledResult.Loading -> {
                            progressBar.isVisible = true
                        }
                        is UnhandledResult.Error -> {
                            progressBar.isGone = true
                            printLog("Error: ${it.exception}")
                        }
                        is UnhandledResult.Success -> {
                            progressBar.isGone = true
                            if (it.data?.result != null && it.data.result.size > 0) {
                                adapter.dataList = it.data.result
                                layoutNoResult.isVisible = false
                            } else {
                                adapter.dataList = arrayListOf()
                                layoutNoResult.isVisible = true
                            }
                        }
                    }
                })
            }
        })

        adapter.itemClickListener = { _, data ->
            if (data is Product) {
                inject<ProductDetailViewModel>().value.productId.value = data.id
                findNavController().navigate(R.id.productDetailFragment)
            }
        }
    }

    private fun initUI(){
        adapter = ProductSearchAdapter()
        rclNotifications.layoutManager = LinearLayoutManager(requireContext())
        rclNotifications.adapter = adapter
    }
}