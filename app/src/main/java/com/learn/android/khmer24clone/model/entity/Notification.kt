package com.learn.android.khmer24clone.model.entity

import com.google.gson.annotations.SerializedName

data class Notification(

	@field:SerializedName("short_description")
	val shortDescription: String? = null,

	@field:SerializedName("thumbnail")
	val thumbnail: Any? = null,

	@field:SerializedName("updated_at")
	val updatedAt: Any? = null,

	@field:SerializedName("created_at")
	val createdAt: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("content")
	val content: Any? = null,

	@field:SerializedName("status")
	val status: Int? = null
)
