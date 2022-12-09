package com.tumininu.movielist.presentation.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200,
    background = Black,
    onPrimary = White
    /* Other default colors to override
    surface = Color.White,

    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)


@Composable
fun MovieListTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    showStatusBar: Boolean = true,
    content: @Composable () -> Unit,
) {
    val systemUiController = rememberSystemUiController()
    val colors = if (darkTheme) {
        systemUiController.setStatusBarColor(color = Black, darkIcons = false)
        systemUiController.setNavigationBarColor(color = Black)
        systemUiController.isStatusBarVisible = showStatusBar
        DarkColorPalette
    } else {
        systemUiController.setStatusBarColor(color = Black, darkIcons = false)
        systemUiController.setNavigationBarColor(color = Black)
        systemUiController.isStatusBarVisible = showStatusBar
        DarkColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}