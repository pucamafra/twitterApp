package com.marlonmafra.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import java.util.Date

data class Tweet(
    @SerializedName("created_at") val createdAt: Date,
    val text: String,
    val user: User
)

@Parcelize
data class User(
    val id: Long,
    @SerializedName("created_at") val createdAt: Date,
    @SerializedName("id_str") val idStr: String,
    val name: String,
    @SerializedName("screen_name") val screenName: String,
    val location: String,
    val description: String,
    val url: String?,
    val verified: Boolean,
    @SerializedName("profile_image_url") val profileImageUrl: String,
    @SerializedName("profile_banner_url") val profileBannerUrl: String?,
    @SerializedName("profile_link_color") val profileLinkColor: String?,
    @SerializedName("friends_count") val following: Int,
    @SerializedName("followers_count") val followers: Int
) : Parcelable