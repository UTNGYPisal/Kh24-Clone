package com.learn.android.khmer24clone.ui.home

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.android.khmer24clone.R
import com.learn.android.khmer24clone.custom.adapter.CategoryAdapter
import com.learn.android.khmer24clone.custom.adapter.SliderPagerAdapter
import com.learn.android.khmer24clone.model.api.UnhandledResult
import com.learn.android.khmer24clone.model.entity.Category
import com.learn.android.khmer24clone.ui.base.BaseFragment
import com.learn.android.khmer24clone.ui.category.sub.SubCategoryViewModel
import com.learn.android.khmer24clone.ui.product.list.ProductListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import org.koin.android.ext.android.inject

class HomeFragment : BaseFragment(R.layout.fragment_home) {

    private val viewModel: HomeViewModel by viewModels()
    private lateinit var sliderAdapter: SliderPagerAdapter
    private lateinit var categoryAdapter: CategoryAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initData()
        initListener()
    }

    override fun onResume() {
        super.onResume()

        requireActivity().run {
            toolbar.isVisible = true
            toolbar.title = getString(R.string.title_home)
            navView.isVisible = true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.search_option_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if (item.itemId == R.id.optionSearch) {
            findNavController().navigate(R.id.action_navigation_home_to_searchFragment)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initUI(){
        setHasOptionsMenu(true)

        sliderAdapter = SliderPagerAdapter()
        viewPagerSlide.adapter = sliderAdapter
        indicatorView.setupWithViewPager(viewPagerSlide)

        categoryAdapter = CategoryAdapter()
        rclCategories.layoutManager = LinearLayoutManager(requireContext())
        rclCategories.adapter = categoryAdapter
    }

    private fun initListener(){
        categoryAdapter.itemClickListener = { position, data ->
            if (data != null && data is Category) {
                if (data.children.isNullOrEmpty()) {
                    inject<ProductListViewModel>().value.category.value = data
                    findNavController().navigate(R.id.action_navigation_home_to_productListFragment)
                } else {
                    inject<SubCategoryViewModel>().value.mainCategory.value = data
                    findNavController().navigate(R.id.action_navigation_home_to_subCategoryFragment)
                }
            }
        }
    }

    private fun initData(){
        viewModel.fetchSlides().observe(viewLifecycleOwner, Observer { slidesResponse ->
            when (slidesResponse) {
                UnhandledResult.Loading -> {

                }
                is UnhandledResult.Error -> {

                }
                is UnhandledResult.Success -> {
                    sliderAdapter.dataList = slidesResponse.data?.result ?: arrayListOf()
                    indicatorView.itemsCount = slidesResponse.data?.result?.size ?: 0
                }
            }
        })

        viewModel.fetchCategories().observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                UnhandledResult.Loading -> {

                }
                is UnhandledResult.Error -> {

                }
                is UnhandledResult.Success -> {
                    categoryAdapter.dataList = response.data?.result ?: arrayListOf()
                }
            }
        })
    }
}
