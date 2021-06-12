package com.fghilmany.moviedb.ui.home

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import com.fghilmany.moviedb.R
import com.fghilmany.moviedb.core.data.Resource
import com.fghilmany.moviedb.core.utils.CAROUSER_INTERVAL
import com.fghilmany.moviedb.databinding.FragmentHomeBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.delay
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        initCarousel()
        initPopular()
        initComingSoon()

    }

    private fun initToolbar() {
        (activity as AppCompatActivity?)?.supportActionBar?.title = resources.getString(R.string.title)
        binding.toolbar.inflateMenu(R.menu.menu_dashboard)
        binding.toolbar.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.action_notification -> {Toast.makeText(context, "Notification", Toast.LENGTH_SHORT).show()}
            }
            true
        }
    }

    private fun initCarousel() {
        val carouselAdapter = CarouselAdapter()
        viewModel.getCarousel().observe(viewLifecycleOwner){
            carouselAdapter.setList(it)
        }
        binding.viewPager.adapter = carouselAdapter
        TabLayoutMediator(binding.intoTabLayout, binding.viewPager)
        { _, _ ->}.attach()
        binding.viewPager.autoScroll(
            lifecycleScope = this.lifecycleScope,
            interval = CAROUSER_INTERVAL
        )
    }

    private fun initComingSoon() {
        val comingSoonAdapter = ComingSoonrAdapter()

        viewModel.getComingSoonMovie().observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    comingSoonAdapter.setList(it.data)
                    comingSoonAdapter.notifyDataSetChanged()
                }
                is Resource.Error -> {}
            }
        }

        binding.rvComingSoon.apply {
            setHasFixedSize(true)
            adapter = comingSoonAdapter
        }
    }

    private fun initPopular() {
        val popularAdapter = PopularAdapter()

        viewModel.getPopularMovie().observe(viewLifecycleOwner){
            when(it){
                is Resource.Loading -> {}
                is Resource.Success -> {
                    popularAdapter.setList(it.data)
                    popularAdapter.notifyDataSetChanged()
                }
                is Resource.Error -> {}
            }
        }

        binding.rvPopular.apply {
            setHasFixedSize(true)
            adapter = popularAdapter
        }
    }

    private fun ViewPager2.autoScroll(lifecycleScope: LifecycleCoroutineScope, interval: Long) {
        lifecycleScope.launchWhenResumed {
            scrollIndefinitely(interval)
        }
    }

    private suspend fun ViewPager2.scrollIndefinitely(interval: Long) {
        delay(interval)
        val numberOfItems = adapter?.itemCount ?: 0
        val lastIndex = if (numberOfItems > 0) numberOfItems - 1 else 0
        val nextItem = if (currentItem == lastIndex) 0 else currentItem + 1

        setCurrentItem(nextItem, true)

        scrollIndefinitely(interval)
    }

}