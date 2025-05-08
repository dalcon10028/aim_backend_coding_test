package co.getaim.security.dto

import jakarta.validation.constraints.Positive

data class SecurityPriceUpdate(
    @field:Positive
    val price: Int,
)
