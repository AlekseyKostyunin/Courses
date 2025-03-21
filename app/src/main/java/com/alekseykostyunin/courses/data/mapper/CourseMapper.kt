package com.alekseykostyunin.courses.data.mapper

import com.alekseykostyunin.courses.data.model.CourseDTO
import com.alekseykostyunin.courses.domain.model.Course

class CourseMapper {
    fun fromDtoToDomain(dto: CourseDTO): Course {
        return Course(
            id = dto.id,
            title = dto.title,
            text = dto.text,
            price = dto.price,
            rate = dto.rate,
            startDate = dto.startDate,
            hasLike = dto.hasLike,
            publishDate = dto.publishDate
        )
    }

    fun fromDomainToDto(entity: Course): CourseDTO {
        return CourseDTO(
            id = entity.id,
            title = entity.title,
            text = entity.text,
            price = entity.price,
            rate = entity.rate,
            startDate = entity.startDate,
            hasLike = entity.hasLike,
            publishDate = entity.publishDate
        )
    }
}