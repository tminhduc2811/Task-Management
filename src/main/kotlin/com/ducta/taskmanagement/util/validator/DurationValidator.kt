package com.ducta.taskmanagement.util.validator

import com.ducta.taskmanagement.dto.ProjectCreateDto
import org.springframework.beans.BeanWrapperImpl
import java.text.SimpleDateFormat
import java.util.*
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass
import kotlin.reflect.jvm.internal.impl.load.kotlin.JvmType


@Target(AnnotationTarget.TYPE, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.CLASS)
@MustBeDocumented
@Constraint(validatedBy = [DurationValidator::class])
annotation class DurationConstraint(
        val message: String = "Invalid date, start date must be before end date",
        val startDate: String = "",
        val endDate: String = "",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)

class DurationValidator : ConstraintValidator<DurationConstraint, Any> {
    var startDate: String = ""
    var endDate: String = ""
    override fun initialize(constraintAnnotation: DurationConstraint) {
        this.startDate = constraintAnnotation.startDate
        this.endDate = constraintAnnotation.endDate
    }
    override fun isValid(value: Any, p1: ConstraintValidatorContext?): Boolean {
        val startDateValue = BeanWrapperImpl(value).getPropertyValue(startDate)
        val endDateValue = BeanWrapperImpl(value).getPropertyValue(endDate)
        val dateFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        try {
            val start: Date = dateFormat.parse(startDateValue as String)
            val end: Date = dateFormat.parse(endDateValue as String)
            if (start.before(end)) {
                return true
            }
            return false
        } catch (ex: Exception) {
            return false
        }
    }

}