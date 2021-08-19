package com.udacity.asteroidradar.main

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)

        val application = requireNotNull(activity).application

        val dataSource = AsteroidDatabase.getInstance(application).asteroidDatabaseDao

        val viewModelFactory = MainViewModelFactory(dataSource, application)

        val asteroidListViewModel = ViewModelProvider(this, viewModelFactory).get(AsteroidListViewModel::class.java)

        val adapter = AsteroidListAdapter(AsteroidListener {
            asteroid -> asteroidListViewModel.onAsteroidClicked(asteroid)
        })

        binding.asteroidRecycler.adapter = adapter

        binding.viewModel = asteroidListViewModel

        asteroidListViewModel.imageUrl.observe(viewLifecycleOwner, Observer { url ->
            Picasso.with(context).load(url).into(binding.activityMainImageOfTheDay)
        })

        asteroidListViewModel.asteroids.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.submitList(it)
            }
        })

        asteroidListViewModel.navigateToAsteroidDetails.observe(viewLifecycleOwner, Observer { asteroid ->
            asteroid?.let {
                this.findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroid))
                asteroidListViewModel.onAsteroidDetailsNavigated()
            }
        })

        binding.lifecycleOwner = this

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
