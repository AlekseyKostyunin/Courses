package com.alekseykostyunin.courses.data.model

import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
data class CoursesResponse(
    @Json(name = "courses")
    val courses: List<CourseDTO>
)