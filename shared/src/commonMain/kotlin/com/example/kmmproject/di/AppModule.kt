package com.example.kmmproject.di

import com.example.kmmproject.contacts.domain.ContactDataSource

expect class AppModule {
    val contactDataSource: ContactDataSource
}