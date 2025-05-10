package co.getaim.account.dto

data class WithdrawResponse(
    val accountId: Long,
    val amount: Int,
    val deposit: Int,
)
