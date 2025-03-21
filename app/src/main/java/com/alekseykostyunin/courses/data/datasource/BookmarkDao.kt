package com.alekseykostyunin.courses.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.alekseykostyunin.courses.data.model.CourseDTO
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(course: CourseDTO)

    @Query("DELETE FROM bookmark_courses WHERE id == :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT * FROM bookmark_courses")
    fun getAllBookmarkCourses(): Flow<List<CourseDTO>>
}