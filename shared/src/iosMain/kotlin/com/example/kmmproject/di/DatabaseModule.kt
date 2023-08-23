package com.example.kmmproject.di

import com.example.kmmproject.data.local.DatabaseDriverFactory
import com.example.kmmproject.data.note.SqlDelightNoteDataSource
import com.example.kmmproject.database.NoteDatabase
import com.example.kmmproject.domain.note.NoteDataSource

class DatabaseModule {

    private val factory by lazy { DatabaseDriverFactory() }
    val noteDataSource: NoteDataSource by lazy {
        SqlDelightNoteDataSource(NoteDatabase(factory.createDriver()))
    }
}