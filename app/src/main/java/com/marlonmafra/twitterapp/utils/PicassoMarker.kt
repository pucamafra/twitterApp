package com.marlonmafra.twitterapp.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
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
    private val markerTargetList = hashSetOf<MarkerTarget>()

    init {
        setup()
    }

    private fun setup() {
        var markerTarget: MarkerTarget
        tweetList.forEachIndexed { index, tweet ->
            val coordinate = tweet.coordinates.toLatLng()
            coordinateList.add(coordinate)

            val marker = googleMap.addMarker(createMarker(coordinate))
            marker.tag = index

            markerTarget = MarkerTarget(marker)
            markerTargetList.add(markerTarget)
            markerTarget.loadMarkerWithUrl(tweet.user.profileImageUrl)

            Picasso.get()
                .load(tweet.user.profileImageUrl)
                .transform(RoundedTransformation(RADIUS))
                .resize(IMAGE_SIZE, IMAGE_SIZE)
                .into(markerTarget)
        }
    }

    private fun createMarker(position: LatLng): MarkerOptions {
        return MarkerOptions()
            .position(position)
    }

    inner class MarkerTarget(
        private val marker: Marker
    ) : Target {

        override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom) {
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(bitmap))
            markerTargetList.remove(this)
        }

        override fun onPrepareLoad(placeHolderDrawable: Drawable?) = Unit

        override fun onBitmapFailed(e: Exception, errorDrawable: Drawable) {
            markerTargetList.remove(this)
        }
    }

    private fun MarkerTarget.loadMarkerWithUrl(url: String) {
        Picasso.get()
            .load(url)
            .transform(RoundedTransformation(RADIUS))
            .resize(IMAGE_SIZE, IMAGE_SIZE)
            .into(this)
    }
}