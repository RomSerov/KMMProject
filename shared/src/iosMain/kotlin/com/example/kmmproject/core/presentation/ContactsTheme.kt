package com.example.kmmproject.core.presentation

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.kmmproject.ui.theme.DarkColorScheme
import com.example.kmmproject.ui.theme.LightColorScheme
import com.example.kmmproject.ui.theme.Typography

@Composable
actual fun ContactsTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme,
        typography = Typography,
        content = content
    )
}