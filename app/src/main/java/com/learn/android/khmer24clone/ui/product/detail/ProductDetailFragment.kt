package com.learn.android.khmer24clone.ui.product.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.android.khmer24clone.BuildConfig
import com.learn.android.khmer24clone.GlideApp
import com.learn.android.khmer24clone.R
import com.learn.android.khmer24clone.common.extension.readableRelativeDate
import com.learn.android.khmer24clone.common.extension.toUSDCurrency
import com.learn.android.khmer24clone.custom.adapter.ProductSliderAdapter
import com.learn.android.khmer24clone.custom.adapter.ProductSpecAdapter
import com.learn.android.khmer24clone.model.api.UnhandledResult
import com.learn.android.khmer24clone.model.entity.Product
import com.learn.android.khmer24clone.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_product_detail.*
import kotlinx.android.synthetic.main.layout_owner_profile.*
import org.koin.android.ext.android.inject

class ProductDetailFragment : BaseFragment(R.layout.fragment_product_detail){

    private val viewModel by inject<ProductDetailViewModel>()
    private lateinit var productSliderAdapter: ProductSliderAdapter
    private lateinit var productSpecAdapter: ProductSpecAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initListener()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.product_detail_option_menu, menu)
    }

    private fun initUI(){
        setHasOptionsMenu(true)
        requireActivity().toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        requireActivity().run {
            toolbar.isVisible = true
            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }

        productSliderAdapter = ProductSliderAdapter()
        viewPagerSlide.adapter = productSliderAdapter
        indicatorView.setupWithViewPager(viewPagerSlide)

        productSpecAdapter = ProductSpecAdapter()
        rclSpecs.layoutManager = LinearLayoutManager(requireContext())
        rclSpecs.adapter = productSpecAdapter

        viewModel.product.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                updateUI(it)
            }
        })
        viewModel.productId.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                getProductDetail(it)
            }
        })
    }

    private fun updateUI(product: Product) {
        productSliderAdapter.dataList = product.images ?: arrayListOf()
        indicatorView.itemsCount = productSliderAdapter.dataList.size

        requireActivity().toolbar.title = product.title ?: getString(R.string.product_detail)

        txtTitle.text = product.title ?: ""
        txtLocation.text = product.province?.name ?: ""
        txtTime.text = product.createdAt?.readableRelativeDate
        txtPrice.text = product.price?.toUSDCurrency() ?: ""

        productSpecAdapter.dataList = product.specs ?: arrayListOf()

        updateOwnerInfo(product)
        txtShortDescription.text = product.shortDescription ?: ""

        txtId.text = "ID: ${product.id ?: ""}"
        txtViewsCount.text = "View: ${product.viewsCount ?: 0}"
    }

    private fun updateOwnerInfo(product: Product){

        product.owner?.run {
            GlideApp.with(requireView())
                .load(BuildConfig.BASE_URL + "/storage/" + avatar)
                .into(imgOwnerProfile)
            txtOwnerName.text = name ?: ""
            txtOwnerTag.text = ""

            txtOwnerPhone.text = phones ?: ""
            txtOwnerProvince.text = province?.name ?: ""
            txtOwnerLocation.text = location ?: ""
        }
    }

    private fun initListener(){
        btnReport.setOnClickListener {
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_EMAIL, arrayOf("info@kh24clone.com.kh"))
            intent.putExtra(Intent.EXTRA_SUBJECT, "Feedback")
            startActivity(intent)
        }
    }

    private fun getProductDetail(id: Int) {
        viewModel.getProductDetail(id).observe(viewLifecycleOwner, Observer {
            when (it) {
                UnhandledResult.Loading -> {

                }
                is UnhandledResult.Error -> {

                }
                is UnhandledResult.Success -> {
                    viewModel.product.value = it.data?.result
                }
            }
        })
    }
}