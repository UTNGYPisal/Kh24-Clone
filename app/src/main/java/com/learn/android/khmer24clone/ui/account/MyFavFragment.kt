package com.learn.android.khmer24clone.ui.account

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.android.khmer24clone.R
import com.learn.android.khmer24clone.common.helper.printLog
import com.learn.android.khmer24clone.custom.adapter.ProductAdapter
import com.learn.android.khmer24clone.model.api.UnhandledResult
import com.learn.android.khmer24clone.model.entity.Product
import com.learn.android.khmer24clone.ui.base.BaseFragment
import com.learn.android.khmer24clone.ui.product.detail.ProductDetailViewModel
import kotlinx.android.synthetic.main.fragment_fav_list.*
import org.koin.android.ext.android.inject

class MyFavFragment: BaseFragment(R.layout.fragment_fav_list) {

    private val viewModel by inject<MyFavViewModel>()
    private lateinit var adapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initData()
        initListener()
    }

    private fun initUI(){
        adapter = ProductAdapter()
        rclProducts.layoutManager = LinearLayoutManager(requireContext())
        rclProducts.adapter = adapter
    }

    private fun initData(){
        viewModel.getFavorites().observe(viewLifecycleOwner, Observer {
            when (it) {
                UnhandledResult.Loading -> {
                    progressBar.isVisible = true
                }
                is UnhandledResult.Error -> {
                    printLog("Error: ")
                    progressBar.isVisible = false
                }
                is UnhandledResult.Success -> {
                    progressBar.isVisible = false
                    adapter.dataList = it.data?.result ?: arrayListOf()
                }
            }
        })
    }

    private fun initListener(){
        adapter.itemClickListener = { position, data ->
            inject<ProductDetailViewModel>().value.apply {
                productId.value = (data as Product).id
            }
            findNavController().navigate(R.id.productDetailFragment)
        }

        adapter.favClickListener = { _, product, imageButton ->
            postToggleFavorite(product, imageButton )
        }
    }

    private fun postToggleFavorite(product: Product, imageButton: ImageButton) {
        mainViewModel.toggleFavorite(product.id!!).observe(viewLifecycleOwner, Observer {
            when (it) {
                is UnhandledResult.Success -> {
                    printLog("postToggleFavorite success")
                    imageButton.setColorFilter(
                        resources.getColor(
                            if (product.isFavorite == true) R.color.colorDefaultIcon else R.color.colorDanger,
                            requireContext().theme)
                    )
                    initData()
                }
            }
        })
    }
}