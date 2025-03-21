package com.alekseykostyunin.courses.data.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alekseykostyunin.courses.data.model.CourseDTO
import com.alekseykostyunin.courses.domain.model.Course

@Database(entities = [CourseDTO::class], version = 1, exportSchema = false)
abstract class BookmarkDataBase : RoomDatabase() {

    abstract fun bookmarkDao(): BookmarkDao

    companion object {
        @Volatile
        private var INSTANCE: BookmarkDataBase? = null

        fun getDatabase(context: Context): BookmarkDataBase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BookmarkDataBase::class.java,
                    "db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}