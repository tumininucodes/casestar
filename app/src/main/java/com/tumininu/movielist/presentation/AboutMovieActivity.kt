package com.tumininu.movielist.presentation

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.media3.common.MediaItem
import androidx.media3.common.util.Util
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.github.kotvertolet.youtubejextractor.YoutubeJExtractor
import com.github.kotvertolet.youtubejextractor.exception.ExtractionException
import com.github.kotvertolet.youtubejextractor.exception.YoutubeRequestException
import com.tumininu.movielist.R
import com.tumininu.movielist.domain.model.Movie
import com.tumininu.movielist.presentation.ui.MainViewModel
import com.tumininu.movielist.presentation.ui.aboutMovie.AboutMovie
import com.tumininu.movielist.presentation.ui.theme.MovieListTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class AboutMovieActivity : ComponentActivity() {

    private var player: ExoPlayer? = null
    private var playWhenReady = true
    private var currentItem = 0
    private var playbackPosition = 0L
    private lateinit var videoId: String
    private lateinit var videoView: PlayerView
    private lateinit var progressView: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about_movie)

        val movie = intent.getSerializableExtra("movie") as Movie

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[MainViewModel::class.java]

        progressView = findViewById(R.id.progress)

        val movieTitle = findViewById<TextView>(R.id.movieTitle)
        movieTitle.text = movie.title

        videoView = findViewById(R.id.video_view)
        videoView.player = player

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getMovieVideos(movieId = movie.id.toString()).collect { videosResponse ->
                if (videosResponse != null) {
                    val officialTrailer = videosResponse.results.filter {
                        it.name?.contains("official trailer", ignoreCase = true) == true
                    }
                    if (officialTrailer.isEmpty()) {
                        videoId = videosResponse.results.first().key.toString()
                        lifecycleScope.launch(Dispatchers.Main) {
                            videoView.visibility = View.VISIBLE
                            initializePlayer()
                        }
                    } else {
                        videoId = officialTrailer.first().key.toString()
                        lifecycleScope.launch(Dispatchers.Main) {
                            videoView.visibility = View.VISIBLE
                            initializePlayer()
                        }
                    }
                }
            }
        }


        val aboutView = findViewById<ComposeView>(R.id.composable)
        aboutView.setContent {
            MovieListTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {
                    AboutMovie(movie = movie)
                }
            }
        }
    }

    public override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            if (this::videoId.isInitialized) {
                initializePlayer()
            }
        }
    }

    public override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <= 23 || player == null) {
            if (this::videoId.isInitialized) {
                initializePlayer()
            }
        }
    }

    public override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    public override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    @SuppressLint("StaticFieldLeak")
    private fun initializePlayer() {
        player = ExoPlayer.Builder(this)
            .build()
            .also { exoPlayer ->
                videoView.player = exoPlayer
                val youtubeJExtractor = YoutubeJExtractor()
                lifecycleScope.launch(Dispatchers.IO) {
                    try {
                        val streamingData = youtubeJExtractor.extract(videoId).streamingData!!
                        val video = streamingData.muxedStreams.first()
                        lifecycleScope.launch(Dispatchers.Main) {
                            val mediaItem = MediaItem.fromUri(video.url)
                            exoPlayer.setMediaItem(mediaItem)
                            exoPlayer.playWhenReady = playWhenReady
                            exoPlayer.seekTo(currentItem, playbackPosition)
                            exoPlayer.prepare()
                            progressView.visibility = View.GONE
                        }

                    } catch (e: ExtractionException) {
                        // Something really bad happened, nothing we can do except just show
                        // some error notification to the user
                    } catch (e: YoutubeRequestException) {
                        // Possibly there are some connection problems, ask user to check
                        // the internet connection and then retry
                    }
                }
            }
    }

    private fun releasePlayer() {
        player?.let { exoPlayer ->
            playbackPosition = exoPlayer.currentPosition
            currentItem = exoPlayer.currentMediaItemIndex
            playWhenReady = exoPlayer.playWhenReady
            exoPlayer.release()
        }
        player = null
    }

}