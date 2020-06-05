package com.ducta.taskmanagement.util.validator


import java.util.regex.Matcher
import java.util.regex.Pattern
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass


@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER)
@MustBeDocumented
@Constraint(validatedBy = [EmailValidator::class])
annotation class EmailConstraint(
        val message: String = "Invalid email",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)

class EmailValidator : ConstraintValidator<EmailConstraint, String> {
    override fun isValid(email: String?, context: ConstraintValidatorContext?): Boolean {
        val pattern: Pattern = Pattern.compile("^([\\w-.]+){1,64}@([\\w&&[^_]]+){2,255}.[a-z]{2,}$")
        if (email == null) {
            return false
        }
        val matcher: Matcher = pattern.matcher(email)
        return matcher.matches()
    }
}