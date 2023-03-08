package com.example.thisweektvshows.di

import android.content.Context
import androidx.room.Room
import com.example.thisweektvshows.db.MoviesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, MoviesDatabase::class.java, "movies_db.db")
            .allowMainThreadQueries()
            .fallbackToDestructiveMigration()
            .build()

//    @Provides
//    @Singleton
//    fun provideCmeDao(database: MoviesDatabase) = database.cmeDao()
}