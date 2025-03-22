package com.alekseykostyunin.courses.data.datasource

import android.content.Context
import com.alekseykostyunin.courses.data.mapper.CourseMapper
import com.alekseykostyunin.courses.domain.model.Course
import com.alekseykostyunin.courses.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

object BookmarkRepositoryImpl : BookmarkRepository {

    override  fun getAllBookmarks(context: Context): Flow<List<Course>> {
        val dao = BookmarkDataBase.getDatabase(context).bookmarkDao()
        val mapper = CourseMapper()
        return  dao.getAllBookmarkCourses()
            .map { listCourseDTO ->
                listCourseDTO.map {
                    mapper.fromDtoToDomain(it)
                }
            }

        // Имитация получения данных из базы данных
//        val list = listOf<Course>(
//            Course(
//            id = 101,
//            title = "3D-дженералист",
//            text= "Освой профессию 3D-дженералиста и стань универсальным специалистом, который умеет создавать 3D-модели, текстуры и анимации, а также может строить карьеру в геймдеве, кино, рекламе или дизайне.",
//            price= "12 000",
//            rate= "3.9",
//            startDate= "2024-09-10",
//            hasLike = false,
//            publishDate= "2024-01-20",
//        )
//        )
//        return flow { emit(list) }
    }

    override suspend fun addCourseOfBookmark(context: Context, course: Course): Boolean {
        course.apply {
            hasLike = true
        }
        val dao = BookmarkDataBase.getDatabase(context).bookmarkDao()
        val mapper = CourseMapper()
        val entity = mapper.fromDomainToDto(course)
        dao.insert(entity)
        return true
    }

    override suspend fun deleteCourseOfBookmark(context: Context, course: Course): Boolean {
        course.apply {
            hasLike = false
        }
        val dao = BookmarkDataBase.getDatabase(context).bookmarkDao()
        val mapper = CourseMapper()
        val entity = mapper.fromDomainToDto(course)
        dao.deleteById(entity.id)
        return true
    }
}