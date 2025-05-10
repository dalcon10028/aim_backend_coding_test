package co.getaim.account.dto

import co.getaim.account.entity.Account
import co.getaim.portfolio.enums.RiskType

data class AccountResponse(
    val accountId: Long,
    val userId: Long,
    val riskType: RiskType,
) {
    companion object {
        fun from(account: Account): AccountResponse =
            AccountResponse(
                accountId = account.id!!,
                userId = account.userId,
                riskType = account.riskType
            )
    }
}
