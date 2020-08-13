package com.marlonmafra.twitterapp.extension

import com.google.android.gms.maps.model.LatLng
import com.marlonmafra.domain.model.Coordinate

fun Coordinate.toLatLng(): LatLng {
    return LatLng(coordinates[0], coordinates[1])
}