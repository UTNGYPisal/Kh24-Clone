package com.learn.android.khmer24clone.ui.product.detail

import android.content.Intent
import android.content.Intent.ACTION_SEND
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.android.khmer24clone.BuildConfig
import com.learn.android.khmer24clone.GlideApp
import com.learn.android.khmer24clone.R
import com.learn.android.khmer24clone.common.extension.readableRelativeDate
import com.learn.android.khmer24clone.common.extension.toUSDCurrency
import com.learn.android.khmer24clone.common.helper.printLog
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
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.product.value = null
        viewModel.productId.value = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.product_detail_option_menu, menu)

        viewModel.product.value?.run {
            menu.findItem(R.id.optionFavorite)?.setIcon(
                if (this.isFavorite == true) R.drawable.ic_baseline_favorite_24
                else R.drawable.ic_favorite_border_black_24dp
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.optionFavorite -> {
                mainViewModel.toggleFavorite(viewModel.product.value!!.id!!).observe(viewLifecycleOwner, Observer {
                    when (it) {
                        is UnhandledResult.Success -> {
                            viewModel.product.value!!.isFavorite = !(viewModel.product.value!!.isFavorite ?: false)
                            //Update menu option icon when isFavorite status is changed
                            setHasOptionsMenu(false)
                            setHasOptionsMenu(true)
                        }
                    }
                })
            }
            R.id.optionShare -> {
                val deepLink = getString(R.string.deeplink_product_detail_of, viewModel.productId.value!!)
                val intent = Intent(ACTION_SEND)
                intent.putExtra(Intent.EXTRA_TEXT, deepLink)
                intent.type = "text/plain"

                val shareIntent = Intent.createChooser(intent, null)
                startActivity(shareIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initData(){
        arguments?.let { bundle ->
            printLog("arguments: ${arguments}")
            bundle.getInt("id").let { productId ->
                printLog("productId: ${productId}")
                if (productId != 0) {
                    viewModel.productId.value = productId
                }
            }
        }
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

        //Update menu option icon when isFavorite status is changed
        setHasOptionsMenu(false)
        setHasOptionsMenu(true)
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
                    showLoading()
                }
                is UnhandledResult.Error -> {
                    hideLoading()
                }
                is UnhandledResult.Success -> {
                    hideLoading()
                    viewModel.product.value = it.data?.result
                }
            }
        })
    }
}