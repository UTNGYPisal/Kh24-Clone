package com.learn.android.khmer24clone.model.entity

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.learn.android.khmer24clone.model.repo.SharedPrefRepo
import java.io.Serializable
import java.lang.Exception

data class User(

    @field:SerializedName("settings")
    val settings: List<Any?>? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("role_id")
    val roleId: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("email_verified_at")
    val emailVerifiedAt: Any? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("avatar")
    val avatar: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("phones")
    val phones: String? = null,

    @field:SerializedName("province")
    val province: Province? = null,

    @field:SerializedName("location")
    val location: String? = null,

    @field:SerializedName("token")
    val token: String? = null

): Serializable {

    companion object {
        var current: User? = null
        get() {
            return SharedPrefRepo.readObject("user.current")
        }
        set(value) {
            field = value
            SharedPrefRepo.saveObject(field, "user.current")
        }


        val isLoggedIn: Boolean
        get() {
            return current != null && !current?.token.isNullOrEmpty()
        }
    }
}
