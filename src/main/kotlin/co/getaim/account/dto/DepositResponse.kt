package co.getaim.account.dto

data class DepositResponse(
    val accountId: Long,
    val amount: Int,
    val balance: Int,
) {
    init {
        require(amount > 0) { "Amount must be greater than 0" }
        require(balance >= 0) { "Balance must be non-negative" }
    }
}
