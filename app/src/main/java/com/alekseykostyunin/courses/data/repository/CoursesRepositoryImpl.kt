package com.alekseykostyunin.courses.data.repository

import android.content.Context
import com.alekseykostyunin.courses.data.mapper.CourseMapper
import com.alekseykostyunin.courses.data.model.CourseDTO
import com.alekseykostyunin.courses.data.model.CoursesResponse
import com.alekseykostyunin.courses.domain.model.Course
import com.alekseykostyunin.courses.domain.repository.CoursesRepository
import kotlinx.serialization.json.Json
import java.io.InputStream

object CoursesRepositoryImpl : CoursesRepository {

    override fun getCoursesRetrofit(): CoursesResponse {
        return RetrofitClient.apiService.loadCourses()
    }

    override fun getCoursesImitation(context: Context): List<Course> {
        val inputStream = context.assets.open("courses.json")
        val mapper = CourseMapper()
        val courses = readCoursesFromJson(inputStream)
        .map { mapper.fromDtoToDomain(it) }
        return courses
    }

    private fun readCoursesFromJson(inputStream: InputStream): List<CourseDTO>  {
        val jsonString = inputStream.bufferedReader().use { it.readText() }
        val courses = Json.decodeFromString<CoursesResponse>(jsonString)
        return courses.courses
    }
}
