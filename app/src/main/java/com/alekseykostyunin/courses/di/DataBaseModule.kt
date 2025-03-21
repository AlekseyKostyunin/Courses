package com.alekseykostyunin.courses.di

import android.content.Context
import androidx.room.Room
import com.alekseykostyunin.courses.data.datasource.BookmarkDao
import com.alekseykostyunin.courses.data.datasource.BookmarkDataBase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideBookmarkDataBase(context: Context): BookmarkDataBase {
        return Room.databaseBuilder(
            context,
            BookmarkDataBase::class.java,
            "db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBookmarkDao(appDatabase: BookmarkDataBase): BookmarkDao {
        return appDatabase.bookmarkDao()
    }
}