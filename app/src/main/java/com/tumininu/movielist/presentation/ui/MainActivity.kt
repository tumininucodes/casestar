package com.tumininu.movielist.presentation.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.tumininu.movielist.databinding.ActivityMainBinding
import com.tumininu.movielist.model.NetworkResult
import com.tumininu.movielist.presentation.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private val adapter = MovieAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        viewModel.getMovies().observe(this) { networkResult ->
            when (networkResult) {
                is NetworkResult.Loading -> {
                    binding.mainProgressBar.visibility = View.VISIBLE
                    binding.fetchMoreProgressBar.visibility = View.GONE
                }
                is NetworkResult.Success -> {
                    binding.rvMovies.adapter = adapter
                    binding.rvMovies.layoutManager = LinearLayoutManager(this)
                    binding.rvMovies.setHasFixedSize(true)
                    adapter.submitList(networkResult.data.results)
                    binding.mainProgressBar.visibility = View.GONE
                    binding.fetchMoreProgressBar.visibility = View.GONE
                }
                is NetworkResult.Error -> {
                    binding.mainProgressBar.visibility = View.GONE
                    Toast.makeText(this, networkResult.error.message.toString(), Toast.LENGTH_LONG)
                        .show()
                    binding.fetchMoreProgressBar.visibility = View.GONE
                }
            }
        }

    }
}