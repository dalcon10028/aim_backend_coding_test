package co.getaim.account.dto

import co.getaim.portfolio.enums.RiskType

data class CreateAccountRequest(
    val riskType: RiskType,
)
