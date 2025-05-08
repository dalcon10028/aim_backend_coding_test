package co.getaim.account.dto

data class AccountResponse(
    val accountId: Long,
    val userId: Long,
    val balance: Int,
)
