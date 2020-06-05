package com.ducta.taskmanagement.util.validator

import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass


@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER)
@MustBeDocumented
@Constraint(validatedBy = [NullableNotBlankValidator::class])
annotation class NullableNotBlank(
        val message: String = "{javax.validation.constraints.NotBlank.message}",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)

class NullableNotBlankValidator : ConstraintValidator<NullableNotBlank, String> {
    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if (value == null) return true
        return value.isNotBlank()
    }
}