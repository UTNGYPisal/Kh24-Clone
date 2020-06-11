package com.learn.android.khmer24clone.ui.category.sub

import androidx.lifecycle.MutableLiveData
import com.learn.android.khmer24clone.model.entity.Category
import com.learn.android.khmer24clone.ui.base.BaseViewModel

class SubCategoryViewModel : BaseViewModel(){

    val mainCategory = MutableLiveData<Category?>()
}