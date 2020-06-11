package com.ducta.taskmanagement.util.validator

import java.text.SimpleDateFormat
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass


@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER)
@MustBeDocumented
@Constraint(validatedBy = [DateValidator::class])
annotation class DateConstraint(
        val message: String = "Invalid date format, must be YYYY-MM-dd",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)

class DateValidator : ConstraintValidator<DateConstraint, String> {
    override fun isValid(inputDate: String?, context: ConstraintValidatorContext?): Boolean {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd")
        return try {
            dateFormat.parse(inputDate)
            true
        } catch (ex: Exception) {
            false
        }
    }
}