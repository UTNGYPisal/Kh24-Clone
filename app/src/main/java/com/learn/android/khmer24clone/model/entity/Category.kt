package com.learn.android.khmer24clone.model.entity

import com.google.gson.annotations.SerializedName

data class Category(
    val id: Int? = null,

    @field:SerializedName("parent_id")
    val parentId: Int? = null,

    val icon: String? = null,

    val name: String? = null,

    val children: ArrayList<Category>? = null
)