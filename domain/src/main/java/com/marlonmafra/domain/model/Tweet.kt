package com.marlonmafra.domain.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class Tweet(
    @SerializedName("created_at") val createdAt: Date,
    val text: String,
    val user: User
)

data class User(
    val id: Long,
    @SerializedName("id_str") val idStr: String,
    val name: String,
    @SerializedName("screen_name") val screenName: String,
    val location: String,
    val description: String,
    val url: String,
    val verified: Boolean,
    @SerializedName("profile_image_url") val profileImageUrl: String
)