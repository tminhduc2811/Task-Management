package com.ducta.taskmanagement.util.reflectionUtil

import java.lang.reflect.Field

class ReflectionUtil {
    companion object {
        fun getPropertyValue(obj: Any, fieldName: String): Any? {
            var result: Any? = null
            try {
                val field: Field = obj.javaClass.getDeclaredField(fieldName)
                field.isAccessible = true
                result = field.get(obj)
            } catch (ignored: NoSuchFieldException) {
            } catch (ignored: IllegalAccessException) {
            }
            return result
        }
    }
}