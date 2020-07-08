package com.learn.android.khmer24clone.ui.post

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import com.learn.android.khmer24clone.R
import com.learn.android.khmer24clone.common.extension.showKeyboard
import com.learn.android.khmer24clone.common.helper.printLog
import com.learn.android.khmer24clone.custom.helper.Alerter
import com.learn.android.khmer24clone.model.api.UnhandledResult
import com.learn.android.khmer24clone.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_post.*
import org.koin.android.ext.android.inject

class PostFragment : BaseFragment(R.layout.fragment_post) {

    private val viewModel by inject<PostViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initData()
        initListener()
    }
    //endregion

    //**************************************************************
    //region Operations
    //**************************************************************
    private fun initUI(){

    }

    private fun initData(){

    }

    private fun initListener(){
        btnSubmit.setOnClickListener {
            if (validateInput()) {
                postProduct()
            }
        }
    }

    private fun validateInput(): Boolean{
        if (etTitle.editText?.text.isNullOrEmpty()) {
            Alerter.showTopErrorDialog(
                requireContext(),
                getString(R.string.err_error),
                getString(R.string.err_title_is_required)
            ).setOnDismissListener {
                etTitle.requestFocus()
                requireActivity().showKeyboard(etTitle.editText!!)
                fragmentRootView.smoothScrollTo(0, etTitle.y.toInt())
            }
            return false
        }

        if (etPrice.editText?.text.isNullOrEmpty()) {
            Alerter.showTopErrorDialog(
                requireContext(),
                getString(R.string.err_error),
                getString(R.string.err_price_is_required)
            ).setOnDismissListener {
                etPrice.requestFocus()
                requireActivity().showKeyboard(etPrice.editText!!)
                fragmentRootView.smoothScrollTo(0, etPrice.y.toInt())
            }
            return false
        }

        if (etCategory.editText?.text.isNullOrEmpty()) {
            Alerter.showTopErrorDialog(
                requireContext(),
                getString(R.string.err_error),
                getString(R.string.err_category_is_required)
            ).setOnDismissListener {
                requireActivity().window.currentFocus?.clearFocus()
                fragmentRootView.smoothScrollTo(0, etCategory.y.toInt())
            }
            return false
        }

        if (etProvince.editText?.text.isNullOrEmpty()) {
            Alerter.showTopErrorDialog(
                requireContext(),
                getString(R.string.err_error),
                getString(R.string.err_province_is_required)
            ).setOnDismissListener {
                requireActivity().window.currentFocus?.clearFocus()
                fragmentRootView.smoothScrollTo(0, etProvince.y.toInt())
            }
            return false
        }


        return true
    }

    private fun postProduct(){
        viewModel.postProduct(
            1,
            1,
            etTitle.editText?.text?.toString() ?: "",
            (etPrice.editText?.text?.toString() ?: "").toDoubleOrNull()?: 0.0,
            etDescription.editText?.text?.toString() ?: ""
        ).observe(viewLifecycleOwner, Observer { task ->
            when (task) {
                UnhandledResult.Loading -> {
                    showLoading()
                }
                is UnhandledResult.Error -> {
                    hideLoading()
                    Alerter.showTopErrorDialog(
                        requireContext(),
                        getString(R.string.err_error),
                        task.exception?.localizedMessage ?: getString(R.string.err_unexpected)
                    )
                }
                is UnhandledResult.Success -> {
                    hideLoading()
                    if (task.data?.result != null && task.data.success) {
                        printLog("postProduct>Success: ${task.data.result}")
                    } else {
                        Alerter.showTopErrorDialog(
                            requireContext(),
                            getString(R.string.err_error),
                            task.data?.message ?: getString(R.string.err_unexpected)
                        )
                    }
                }
            }
        })
    }
    //endregion
}