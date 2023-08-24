package com.fernanortega.plantscommerce.domain.model

sealed class ValidationResult<out T> {
    object Valid: ValidationResult<Nothing>()
    data class InvalidResult<T>(val invalidResult: T): ValidationResult<T>()
}