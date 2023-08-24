package com.example.kmmproject.core.presentation

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.ImageBitmap

@Composable
expect fun rememberBitmapFromBytes(bytes: ByteArray?): ImageBitmap?