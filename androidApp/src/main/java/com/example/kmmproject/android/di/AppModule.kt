package com.example.kmmproject.android.di

import android.app.Application
import com.example.kmmproject.data.local.DatabaseDriverFactory
import com.example.kmmproject.data.note.SqlDelightNoteDataSource
import com.example.kmmproject.database.NoteDatabase
import com.example.kmmproject.domain.note.NoteDataSource
import com.squareup.sqldelight.db.SqlDriver
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return DatabaseDriverFactory(context = app).createDriver()
    }

    @Provides
    @Singleton
    fun provideNoteDataSource(driver: SqlDriver): NoteDataSource {
        return SqlDelightNoteDataSource(db = NoteDatabase(driver = driver))
    }
}