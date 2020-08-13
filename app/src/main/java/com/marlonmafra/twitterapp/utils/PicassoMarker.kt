package com.marlonmafra.twitterapp.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.marlonmafra.domain.model.Tweet
import com.marlonmafra.twitterapp.extension.toLatLng
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class PicassoMarker(
    private val googleMap: GoogleMap,
    private val tweetList: List<Tweet>
) {

    companion object {
        const val IMAGE_SIZE = 100
        const val RADIUS = 50f
    }

    val coordinateList = mutableListOf<LatLng>()
    val targetList = mutableListOf<Target>()

    init {
        setup()
    }

    private fun setup() {
        tweetList.forEach { tweet ->
            val position = tweet.coordinates.toLatLng()
            coordinateList.add(position)

            val target = object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
                    googleMap.addMarker(createMarker(position, bitmap))
                }

                override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) = Unit

                override fun onPrepareLoad(placeHolderDrawable: Drawable?) = Unit
            }

            Picasso.get().load(tweet.user.profileImageUrl)
                .transform(RoundedTransformation(RADIUS))
                .resize(IMAGE_SIZE, IMAGE_SIZE)
                .into(target)

            targetList.add(target)
        }
    }

    private fun createMarker(position: LatLng, iconId: Bitmap): MarkerOptions {
        return MarkerOptions()
            .position(position)
            .icon(BitmapDescriptorFactory.fromBitmap(iconId))
    }
}