package com.fernanortega.plantscommerce.domain.usecases.validators

import android.util.Patterns
import com.fernanortega.plantscommerce.domain.model.ValidationResult

class EmailValidatorUseCase {
    fun validate(email: String): ValidationResult<EmailError> {
        if(email.isBlank())
            return ValidationResult.InvalidResult(EmailError.BLANK)

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
            return ValidationResult.InvalidResult(EmailError.NOT_MATCHES_EMAIL)

        return ValidationResult.Valid
    }
}

enum class EmailError {
    BLANK,
    NOT_MATCHES_EMAIL
}