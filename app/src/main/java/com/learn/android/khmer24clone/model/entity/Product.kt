package com.learn.android.khmer24clone.model.entity

import com.google.gson.annotations.SerializedName

data class Product(

	@field:SerializedName("owner")
	val owner: User? = null,

	@field:SerializedName("short_description")
	val shortDescription: String? = null,

	@field:SerializedName("thumbnail")
	val thumbnail: String? = null,

	@field:SerializedName("images")
	val images: ArrayList<String>? = null,

	@field:SerializedName("category_id")
	val categoryId: Int? = null,

	@field:SerializedName("updated_at")
	val updatedAt: String? = null,

	@field:SerializedName("owner_id")
	val ownerId: Int? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("is_favorite")
	var isFavorite: Boolean? = false,

	val province: Province? = null,
	val category: Category? = null,
	val price: Double? = null,

	@field:SerializedName("views_count")
	val viewsCount: Int? = null,

	val specs: ArrayList<ProductSpec>? = null
)

