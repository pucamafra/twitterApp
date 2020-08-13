package com.marlonmafra.twitterapp.features.home.timeline

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.marlonmafra.twitterapp.R
import com.marlonmafra.twitterapp.TwitterApp
import com.marlonmafra.twitterapp.features.home.HomeViewModel
import com.marlonmafra.twitterapp.features.profile.ProfileActivity
import eu.davidea.flexibleadapter.FlexibleAdapter
import kotlinx.android.synthetic.main.activity_main.*

class TimelineFragment : Fragment() {

    private val homeViewModel: HomeViewModel by activityViewModels()
    private val adapter = FlexibleAdapter(ArrayList())

    override fun onAttach(context: Context) {
        TwitterApp.component?.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_timeline, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLayout()
        setupObserver()
    }

    private fun setupObserver() {
        homeViewModel.tweetList.observe(viewLifecycleOwner, Observer {
            adapter.updateDataSet(it)
        })

        homeViewModel.userClicked.observe(viewLifecycleOwner, Observer {
            startActivity(ProfileActivity.createInstance(requireActivity(), it))
        })
    }

    private fun setupLayout() {
        val layoutManager = LinearLayoutManager(context)
        val dividerItemDecoration = DividerItemDecoration(context, layoutManager.orientation)
        tweetListView.layoutManager = layoutManager
        tweetListView.addItemDecoration(dividerItemDecoration)
        tweetListView.adapter = adapter
    }
}