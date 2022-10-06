package com.tumininu.movielist.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tumininu.movielist.data.NetworkUtil
import com.tumininu.movielist.databinding.ActivityMainBinding
import com.tumininu.movielist.model.NetworkResult
import com.tumininu.movielist.presentation.HomeViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: HomeViewModel
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val adapter = MovieAdapter(this)
    private var page = 1
    private val layoutManager = LinearLayoutManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[HomeViewModel::class.java]

        if (NetworkUtil.isNetworkAvailable(this)) {
            viewModel.getMovies().observe(this) { networkResult ->
                when (networkResult) {
                    is NetworkResult.Loading -> {
                        if (viewModel.moviesList.isNotEmpty()) {
                            binding.fetchMoreProgressBar.visibility = View.VISIBLE
                        }
                    }
                    is NetworkResult.Success -> {
                        viewModel.moviesList.addAll(networkResult.data.results!!)
                        adapter.submitList(viewModel.moviesList)
                        val scrollPosition =
                            viewModel.moviesList.size - networkResult.data.results!!.size - 1
                        layoutManager.scrollToPosition(scrollPosition)
                        binding.rvMovies.layoutManager = layoutManager
                        binding.rvMovies.adapter = adapter
                        binding.rvMovies.setHasFixedSize(true)
                        binding.mainProgressBar.visibility = View.GONE
                        binding.fetchMoreProgressBar.visibility = View.GONE
                    }
                    is NetworkResult.Error -> {
                        binding.mainProgressBar.visibility = View.GONE
                        Toast.makeText(
                            this,
                            networkResult.error.message.toString(),
                            Toast.LENGTH_LONG
                        )
                            .show()
                        binding.fetchMoreProgressBar.visibility = View.GONE
                    }
                }
            }

            binding.rvMovies.addOnScrollListener(object :
                RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (recyclerView.canScrollVertically(1).not() && dy > 1) {
                        page += 1
                        viewModel.fetchMovies(
                            page
                        )
                        binding.fetchMoreProgressBar.visibility = View.VISIBLE
                    }
                }
            })

        } else {
            binding.mainProgressBar.visibility = View.GONE
            Toast.makeText(this, "Please connect to the internet and try again", Toast.LENGTH_SHORT)
                .show()
        }

    }
}