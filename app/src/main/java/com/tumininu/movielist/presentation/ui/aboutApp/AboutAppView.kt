package com.tumininu.movielist.presentation.ui.aboutApp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tumininu.movielist.R
import com.tumininu.movielist.presentation.ui.theme.Black
import com.tumininu.movielist.presentation.ui.theme.White


@Composable
fun AboutAppView(activity: Activity, modifier: Modifier = Modifier) {
    Scaffold(topBar = {
        TopAppBar(backgroundColor = Black) {
            IconButton(onClick = { activity.finish() }) {
                Icon(Icons.Default.ArrowBack, "Back", tint = White)
            }
            Text(
                text = "About Casestar",
                fontSize = 21.sp,
                color = White,
            )
        }
    }) { padding ->
        Column(modifier = modifier.padding(padding)) {
            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier.padding(16.dp)) {
                    Text(text = "Designed and developed by")
                    Spacer(modifier = modifier.height(16.dp))
                    Image(painter = painterResource(id = R.drawable.profile),
                        contentDescription = "profile",
                        contentScale = ContentScale.Crop,
                        modifier = modifier
                            .size(90.dp)
                            .clip(CircleShape))
                    Spacer(modifier = modifier.height(12.dp))
                    Text(text = "Tumi Ojo", fontSize = 20.sp)
                }
            }

            Spacer(modifier = modifier.height(10.dp))

            Card(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(10.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = modifier.padding(16.dp)) {
                    Text(text = "Connect with me on")
                    Spacer(modifier = modifier.height(20.dp))

                    Row {
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = modifier.clickable {
                                val url = "https://twitter.com/tumi_ojo"
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.data = Uri.parse(url)
                                activity.startActivity(intent)
                            }) {
                            Image(painter = painterResource(id = R.drawable.ic_twitter),
                                contentDescription = "profile",
                                contentScale = ContentScale.Crop,
                                colorFilter = ColorFilter.tint(White),
                                modifier = modifier
                                    .size(30.dp))
                            Spacer(modifier = modifier.height(4.dp))
                            Text(text = "Twitter", fontSize = 14.sp)
                        }

                        Spacer(modifier = modifier.width(60.dp))

                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = modifier.clickable {
                                val url = "https://github.com/tumininucodes"
                                val intent = Intent(Intent.ACTION_VIEW)
                                intent.data = Uri.parse(url)
                                activity.startActivity(intent)
                            }) {
                            Image(painter = painterResource(id = R.drawable.ic_github),
                                contentDescription = "profile",
                                contentScale = ContentScale.Crop,
                                colorFilter = ColorFilter.tint(White),
                                modifier = modifier
                                    .size(30.dp))
                            Spacer(modifier = modifier.height(4.dp))
                            Text(text = "Github", fontSize = 14.sp)
                        }
                    }
                }
            }
        }

    }
}