package com.ducta.taskmanagement.util.validator

import org.springframework.beans.BeanWrapperImpl
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass


@Target(AnnotationTarget.TYPE, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.CLASS)
@MustBeDocumented
@Constraint(validatedBy = [FieldsMatchValidator::class])
annotation class FieldsMatch(
        val message: String = "Invalid date, start date must be before end date",
        val field: String = "",
        val matchedField: String = "",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)

class FieldsMatchValidator : ConstraintValidator<FieldsMatch, Any> {
    var field: String = ""
    var matchedField: String = ""
    override fun initialize(constraintAnnotation: FieldsMatch) {
        this.field = constraintAnnotation.field
        this.matchedField = constraintAnnotation.matchedField
    }
    override fun isValid(value: Any, context: ConstraintValidatorContext): Boolean {
        val field = BeanWrapperImpl(value).getPropertyValue(field)
        val matchedField = BeanWrapperImpl(value).getPropertyValue(matchedField)
        if (field == matchedField) {
            return true
        }
        return false
    }

}