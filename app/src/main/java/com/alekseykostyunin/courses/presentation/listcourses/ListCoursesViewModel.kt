package com.alekseykostyunin.courses.presentation.listcourses

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alekseykostyunin.courses.domain.model.Course
import com.alekseykostyunin.courses.domain.repository.CoursesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ListCoursesViewModel(
    private val context: Context,
    private val coursesRepository: CoursesRepository,
) : ViewModel() {

    private val _courses = MutableStateFlow<List<Course>>(emptyList())
    val courses: StateFlow<List<Course>> = _courses

    init {
        loadCourses()
    }

    private fun loadCourses() {
        viewModelScope.launch {
            try {
                _courses.value = coursesRepository.getCoursesImitation(context)
//                _courses.value = coursesRepository.getCoursesRetrofit().courses.map {
//                    Course(
//                        id = it.id,
//                        title = it.title,
//                        text = it.text,
//                        price = it.price,
//                        rate = it.rate,
//                        startDate = it.startDate,
//                        hasLike = it.hasLike,
//                        publishDate = it.publishDate,)
//                }

            } catch (e: Exception) {
                Log.e("TEST_ListCoursesViewModel", e.message, e)
            }
        }
    }

    fun filterCourses() {
        _courses.value = _courses.value.sortedByDescending { it.startDate }
    }

}