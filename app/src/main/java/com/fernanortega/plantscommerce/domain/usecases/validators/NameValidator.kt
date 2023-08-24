package com.fernanortega.plantscommerce.domain.usecases.validators

import com.fernanortega.plantscommerce.domain.model.ValidationResult

class NameValidator {
    fun validate(name: String): ValidationResult<NameError> {
        if(name.isBlank())
            return ValidationResult.InvalidResult(NameError.BLANK)

        if(name.any { char -> !char.isLetter() })
            return ValidationResult.InvalidResult(NameError.HAS_SPECIAL_CHARACTERS)

        return ValidationResult.Valid
    }
}

enum class NameError {
    BLANK,
    HAS_SPECIAL_CHARACTERS
}