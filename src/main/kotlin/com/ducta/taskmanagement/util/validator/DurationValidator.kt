package com.ducta.taskmanagement.util.validator

import org.springframework.beans.BeanWrapperImpl
import java.text.SimpleDateFormat
import java.util.*
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass


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
    override fun isValid(value: Any, context: ConstraintValidatorContext): Boolean {
        val startDateValue = BeanWrapperImpl(value).getPropertyValue(startDate) as String?
        val endDateValue = BeanWrapperImpl(value).getPropertyValue(endDate) as String?
        if (startDateValue.isNullOrBlank() || endDateValue.isNullOrBlank()) {
            return true
        }
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        try {
            val start: Date = dateFormat.parse(startDateValue)
            val end: Date = dateFormat.parse(endDateValue)
            if (start.before(end)) {
                return true
            }
            return false
        } catch (ex: Exception) {
            context.disableDefaultConstraintViolation()
            context.buildConstraintViolationWithTemplate("Invalid date format, must be YYYY-MM-dd").addConstraintViolation()
            return false
        }
    }

}