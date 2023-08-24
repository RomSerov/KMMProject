package com.example.kmmproject.di

import com.example.kmmproject.contacts.data.SqlDelightContactDataSource
import com.example.kmmproject.contacts.domain.ContactDataSource
import com.example.kmmproject.core.data.DatabaseDriverFactory
import com.example.kmmproject.core.data.ImageStorage
import com.example.kmmproject.database.ContactDatabase

actual class AppModule {

    actual val contactDataSource: ContactDataSource by lazy {
        SqlDelightContactDataSource(
            db = ContactDatabase(
                driver = DatabaseDriverFactory().create()
            ),
            imageStorage = ImageStorage()
        )
    }
}