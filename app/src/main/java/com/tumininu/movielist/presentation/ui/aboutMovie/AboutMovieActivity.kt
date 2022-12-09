package com.tumininu.movielist.presentation.ui.aboutMovie

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.tumininu.movielist.domain.model.CastResponse
import com.tumininu.movielist.domain.model.Movie
import com.tumininu.movielist.domain.model.NetworkResult
import com.tumininu.movielist.presentation.MainViewModel
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

        val viewModel = ViewModelProvider(this,
            ViewModelProvider.NewInstanceFactory())[MainViewModel::class.java]

        progressView = findViewById(R.id.progress)
        val ivBack = findViewById<ImageView>(R.id.closeAboutMovie)
        videoView = findViewById(R.id.video_view)
        videoView.player = player

        ivBack.setOnClickListener {
            finish()
        }

        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.getMovieVideos(movieId = movie.id.toString()).collect { videosResponse ->
                if (videosResponse != null && videosResponse.results.isNotEmpty()) {
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

        val composeView = findViewById<ComposeView>(R.id.composable)
        composeView.setContent {
            MovieListTheme {
                Surface(modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background) {

                    Column(Modifier.verticalScroll(rememberScrollState())) {
                        AboutMovie(movie = movie)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(text = "Cast",
                            fontWeight = FontWeight.Bold,
                            fontSize = 21.sp,
                            modifier = Modifier.padding(horizontal = 16.dp))

                        val data: MutableState<NetworkResult<CastResponse>> =
                            remember { mutableStateOf(NetworkResult.Loading) }
                        lifecycleScope.launch {
                            viewModel.getCast(movie.id.toString()).collect {
                                data.value = it
                            }
                        }

                        CastView(data)

                    }
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
        player = ExoPlayer.Builder(this).build().also { exoPlayer ->
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