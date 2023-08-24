package com.example.kmmproject.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.ui.platform.LocalContext
import com.example.kmmproject.App
import com.example.kmmproject.core.presentation.ImagePickerFactory
import com.example.kmmproject.di.AppModule

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App(
                darkTheme = isSystemInDarkTheme(),
                dynamicColor = true,
                appModule = AppModule(context = LocalContext.current.applicationContext),
                imagePicker = ImagePickerFactory().createPicker()
            )
        }
    }
}
