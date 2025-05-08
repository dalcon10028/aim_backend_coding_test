package co.getaim.account.dto

import jakarta.validation.constraints.Positive

data class WithdrawRequest(
    @field:Positive
    val amount: Int,
)
