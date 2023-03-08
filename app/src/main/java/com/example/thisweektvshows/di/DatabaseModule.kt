package com.example.thisweektvshows.di

import android.content.Context
import androidx.room.Room
import com.example.thisweektvshows.db.MoviesDao
import com.example.thisweektvshows.db.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context): MoviesDatabase =
        Room.databaseBuilder(context, MoviesDatabase::class.java, "movies_db.db")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Reusable
    fun provideCmeDao(database: MoviesDatabase): MoviesDao = database.getMoviesDao()
}