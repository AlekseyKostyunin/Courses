package com.alekseykostyunin.courses.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "bookmark_courses")
data class CourseDTO(
    @PrimaryKey
    @Json(name = "id")
    val id: Int,
    @Json(name = "title")
    val title: String,
    @Json(name = "text")
    val text: String,
    @Json(name = "price")
    val price: String,
    @Json(name = "rate")
    val rate: String,
    @Json(name = "startDate")
    val startDate: String,
    @Json(name = "hasLike")
    val hasLike: Boolean,
    @Json(name = "publishDate")
    val publishDate: String,
)
