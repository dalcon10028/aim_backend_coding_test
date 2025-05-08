package co.getaim.account.dto

import jakarta.validation.constraints.Positive

data class DepositRequest(
    @field:Positive
    val amount: Int,
)
