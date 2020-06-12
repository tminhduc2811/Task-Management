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
@Constraint(validatedBy = [PasswordValidator::class])
annotation class PasswordConstraint(
        val message: String = "Your password is not satisfied",
        val groups: Array<KClass<*>> = [],
        val payload: Array<KClass<out Payload>> = []
)

class PasswordValidator : ConstraintValidator<PasswordConstraint, String> {
    override fun isValid(password: String?, context: ConstraintValidatorContext?): Boolean {
        // Password must be between 8 & 40 characters, at least 1 digit, lower key, upper key and special character
        val pattern: Pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%^&+=])(?=\\S+\$).{8,40}\$")
        if (password == null) {
            return false
        }
        val matcher: Matcher = pattern.matcher(password)
        return matcher.matches()
    }
}