package com.ducta.taskmanagement.util.validator

import com.ducta.taskmanagement.domain.Priority
import com.ducta.taskmanagement.domain.Status
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER)
@MustBeDocumented
@Constraint(validatedBy = [EnumValidator::class])
annotation class EnumConstraint(
        val message: String = "Invalid enum type",
        val field: String = "status",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)

class EnumValidator : ConstraintValidator<EnumConstraint, Int> {
    var fieldName: String = ""

    override fun initialize(constraintAnnotation: EnumConstraint) {
        this.fieldName = constraintAnnotation.field
    }

    override fun isValid(enumValue: Int?, context: ConstraintValidatorContext): Boolean {
        if (fieldName == "status" && (Status.values().any() { it.ordinal == enumValue})) {
            return true
        } else if (fieldName == "priority" && (Priority.values().any() { it.ordinal == enumValue })){
            return true
        }
        context.disableDefaultConstraintViolation()
        context.buildConstraintViolationWithTemplate("Invalid $fieldName value").addConstraintViolation()
        return false
    }
}