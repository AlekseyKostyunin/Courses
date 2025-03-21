package com.alekseykostyunin.courses.domain.repository

import android.content.Context
import com.alekseykostyunin.courses.data.model.CoursesResponse
import com.alekseykostyunin.courses.domain.model.Course

interface CoursesRepository {
    fun getCoursesImitation(context: Context): List<Course>
    fun getCoursesRetrofit(): CoursesResponse
}