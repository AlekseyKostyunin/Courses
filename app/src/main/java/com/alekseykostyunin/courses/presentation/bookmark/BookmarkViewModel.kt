package com.alekseykostyunin.courses.presentation.bookmark

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alekseykostyunin.courses.domain.model.Course
import com.alekseykostyunin.courses.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class BookmarkViewModel(
    @SuppressLint("StaticFieldLeak")
    private val context: Context,
    private val bookmarkRepository: BookmarkRepository
) : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    init {
        getBookmarks()
    }

    private fun getBookmarks() {
        viewModelScope.launch {
            try {
                val bookmarks = bookmarkRepository.getAllBookmarks(context)
                bookmarks.collectLatest {
                    _courses.value = it
                }
                Log.d("TEST_ListCoursesViewModel", _courses.value.toString())
            } catch (e: Exception) {
                Log.e("TEST_ListCoursesViewModel", e.message, e)
            }
        }
    }

    fun addCourseOfBookmark(course: Course) {
        viewModelScope.launch {
            try {
                bookmarkRepository.addCourseOfBookmark(context, course)
            } catch (e: Exception) {
                Log.e("TEST_ListCoursesViewModel", e.message, e)
            }
        }
    }

    fun deleteCourseOfBookmark(course: Course) {
        viewModelScope.launch {
            try {
                bookmarkRepository.deleteCourseOfBookmark(context, course)
            } catch (e: Exception) {
                Log.e("TEST_ListCoursesViewModel", e.message, e)
            }
        }
    }

}