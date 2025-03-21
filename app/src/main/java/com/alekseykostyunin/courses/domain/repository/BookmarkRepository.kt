package com.alekseykostyunin.courses.domain.repository

import android.content.Context
import com.alekseykostyunin.courses.domain.model.Course
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {
    fun getAllBookmarks(context: Context): Flow<List<Course>>
    suspend fun addCourseOfBookmark(context: Context, course: Course): Boolean
    suspend fun deleteCourseOfBookmark(context: Context, course: Course): Boolean
}