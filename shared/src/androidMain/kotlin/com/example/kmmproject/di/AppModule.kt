package com.example.kmmproject.di

import android.content.Context
import com.example.kmmproject.contacts.data.SqlDelightContactDataSource
import com.example.kmmproject.contacts.domain.ContactDataSource
import com.example.kmmproject.core.data.DatabaseDriverFactory
import com.example.kmmproject.core.data.ImageStorage
import com.example.kmmproject.database.ContactDatabase

actual class AppModule(
    private val context: Context
) {

    actual val contactDataSource: ContactDataSource by lazy {
        SqlDelightContactDataSource(
            db = ContactDatabase(
                driver = DatabaseDriverFactory(context).create(),
            ),
            imageStorage = ImageStorage(context = context)
        )
    }
}