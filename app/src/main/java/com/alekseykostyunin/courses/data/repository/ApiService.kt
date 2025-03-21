package com.alekseykostyunin.courses.data.repository

import com.alekseykostyunin.courses.data.model.CoursesResponse
import retrofit2.http.GET

interface ApiService {

    @GET("courses")
    fun loadCourses(): CoursesResponse
}