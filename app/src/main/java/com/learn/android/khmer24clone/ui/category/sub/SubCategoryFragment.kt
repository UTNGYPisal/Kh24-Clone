package com.learn.android.khmer24clone.ui.category.sub

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.android.khmer24clone.BuildConfig
import com.learn.android.khmer24clone.GlideApp
import com.learn.android.khmer24clone.R
import com.learn.android.khmer24clone.custom.adapter.CategoryAdapter
import com.learn.android.khmer24clone.model.entity.Category
import com.learn.android.khmer24clone.ui.base.BaseFragment
import com.learn.android.khmer24clone.ui.product.list.ProductListViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_sub_category.*
import org.koin.android.ext.android.inject

class SubCategoryFragment : BaseFragment(R.layout.fragment_sub_category) {

    private lateinit var subcategoryAdapter: CategoryAdapter
    private val viewModel by inject<SubCategoryViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        updateUI()
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

    private fun initUI(){
        rclSubCategories.layoutManager = LinearLayoutManager(requireContext())
        subcategoryAdapter = CategoryAdapter()
        rclSubCategories.adapter = subcategoryAdapter
    }

    private fun updateUI(){
        viewModel.mainCategory.observe(viewLifecycleOwner, Observer { category ->
            if (category != null) {
                GlideApp.with(requireView())
                    .load(BuildConfig.BASE_URL + category.icon)
                    .into(imgCategory)
                txtTitle.text = "View all in ${category.name}"

                subcategoryAdapter.dataList = category.children ?: arrayListOf()
            }
        })
    }

    private fun initListener(){
        lytMainCategory.setOnClickListener {
            inject<ProductListViewModel>().value.category.value = viewModel.mainCategory.value!!
            findNavController().navigate(R.id.action_subCategoryFragment_to_productListFragment)
        }

        subcategoryAdapter.itemClickListener = { _, data ->
            inject<ProductListViewModel>().value.category.value = data as Category
            findNavController().navigate(R.id.action_subCategoryFragment_to_productListFragment)
        }
    }
}