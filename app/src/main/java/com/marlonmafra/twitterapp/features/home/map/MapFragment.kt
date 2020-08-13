package com.marlonmafra.twitterapp.features.home.map

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.marlonmafra.twitterapp.R
import com.marlonmafra.twitterapp.TwitterApp
import com.marlonmafra.twitterapp.features.home.HomeViewModel
import com.marlonmafra.twitterapp.features.profile.ProfileActivity
import com.marlonmafra.twitterapp.utils.PicassoMarker

class MapFragment : Fragment(), OnMapReadyCallback {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private var picassoMarker: PicassoMarker? = null

    override fun onAttach(context: Context) {
        TwitterApp.component?.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObserver()
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun setupObserver() {
        homeViewModel.userClicked.observe(viewLifecycleOwner, Observer {
            startActivity(ProfileActivity.createInstance(requireActivity(), it))
        })
    }

    override fun onMapReady(googleMap: GoogleMap) {
        homeViewModel.tweeList.observe(viewLifecycleOwner, Observer { tweetList ->
            picassoMarker = PicassoMarker(googleMap, tweetList)
            picassoMarker?.let { adjustBoundsOnMap(googleMap, it.coordinateList) }
            googleMap.setOnMarkerClickListener {
                val user = tweetList[it.tag as Int].user
                startActivity(ProfileActivity.createInstance(requireContext(), user))
                false
            }
        })
    }

    private fun adjustBoundsOnMap(map: GoogleMap, markersCoordinateList: List<LatLng>) {
        val bounds = getLatLngBounds(markersCoordinateList)
        val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 0)
        map.moveCamera(cameraUpdate)
        map.moveCamera(CameraUpdateFactory.zoomTo(map.cameraPosition.zoom - 1.5f))
    }

    private fun getLatLngBounds(markersCoordinateList: List<LatLng>): LatLngBounds {
        val builder = LatLngBounds.Builder()
        for (markerCoordinates in markersCoordinateList) {
            builder.include(markerCoordinates)
        }

        return builder.build()
    }

    override fun onDestroy() {
        picassoMarker = null
        super.onDestroy()
    }
}