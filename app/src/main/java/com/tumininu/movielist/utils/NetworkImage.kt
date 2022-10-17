package com.tumininu.movielist.utils

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.tumininu.movielist.R

@Composable
fun NetworkImage(url: String?, modifier: Modifier) {

    var image by remember { mutableStateOf<ImageBitmap?>(null) }
    var drawable by remember { mutableStateOf<Drawable?>(null) }

    DisposableEffect(url) {
        val picasso = Picasso.get()

        val target = object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                drawable = placeHolderDrawable
            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                drawable = errorDrawable
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                image = bitmap?.asImageBitmap()
            }
        }

        picasso
            .load(url)
            .resize(360, 600)
            .centerCrop()
            .onlyScaleDown()
            .placeholder(R.drawable.ic_launcher_foreground)
            .error(R.drawable.image)
            .into(target)

        onDispose {
            image = null
            drawable = null
            picasso.cancelRequest(target)
        }
    }

    if (image != null) {
        Image(bitmap = image!!, contentDescription = "image")
    } else if (drawable != null) {
        Canvas(modifier = modifier) {
            drawIntoCanvas { canvas ->
                drawable!!.draw(canvas.nativeCanvas)
            }
        }
    }
}