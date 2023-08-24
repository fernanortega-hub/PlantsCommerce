package com.fernanortega.plantscommerce.domain.usecases.validators

import com.fernanortega.plantscommerce.domain.model.ValidationResult

class PasswordValidatorUseCase {
    fun validate(password: String): ValidationResult<PasswordError> {
        if(password.isBlank())
            return ValidationResult.InvalidResult(PasswordError.BLANK)

        if(password.length < 8)
            return ValidationResult.InvalidResult(PasswordError.LOW_CHARACTERS)

        return ValidationResult.Valid
    }
}

enum class PasswordError {
    BLANK,
    LOW_CHARACTERS
}