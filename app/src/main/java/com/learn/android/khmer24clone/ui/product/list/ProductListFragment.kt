package com.learn.android.khmer24clone.ui.product.list

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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_product_list.*
import org.koin.android.ext.android.inject

class ProductListFragment : BaseFragment(R.layout.fragment_product_list){

    private val viewModel by inject<ProductListViewModel>()
    private lateinit var productAdapter: ProductAdapter
    
    //**************************************************************
    //region Lifecycle
    //**************************************************************
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initData()
        initListener()
    }

    override fun onResume() {
        super.onResume()
        requireActivity().run {
            this.toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            this.toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
    //endregion

    //**************************************************************
    //region Init
    //**************************************************************
    private fun initUI(){
        productAdapter = ProductAdapter()
        rclProducts.layoutManager = LinearLayoutManager(requireContext())
        rclProducts.adapter = productAdapter
    }

    private fun initListener(){
        productAdapter.itemClickListener = { position, data ->
            inject<ProductDetailViewModel>().value.apply {
                productId.value = (data as Product).id
            }
            findNavController().navigate(R.id.action_productListFragment_to_productDetailFragment)
        }

        productAdapter.favClickListener = { _, product, imageButton ->
            postToggleFavorite(product, imageButton )
        }
    }

    private fun initData(){
        viewModel.fetchProducts(viewModel.category.value?.id).observe(viewLifecycleOwner, Observer {
            when (it) {
                UnhandledResult.Loading -> {
                    progressBar.isVisible = true
                }
                is UnhandledResult.Error -> {
                    progressBar.isVisible = false
                }
                is UnhandledResult.Success -> {
                    progressBar.isVisible = false
                    if (it.data?.result.isNullOrEmpty()) {
                        layoutNoResult.isVisible = true
                    } else {
                        layoutNoResult.isVisible = false
                        productAdapter.dataList = it.data?.result ?: arrayListOf()
                    }
                }
            }
        })
    }
    //endregion

    //**************************************************************
    //region Operations
    //**************************************************************
    /**
     * Save to favorite if not yet saved,
     * Else, remove from favorite
     */
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
                }
            }
        })
    }
    //endregion

}