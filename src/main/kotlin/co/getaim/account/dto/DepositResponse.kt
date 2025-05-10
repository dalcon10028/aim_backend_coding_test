package co.getaim.account.dto

data class DepositResponse(
    val accountId: Long,
    val amount: Int,
    val deposit: Int,
) {
    init {
        require(amount > 0) { "Amount must be greater than 0" }
        require(deposit >= 0) { "Deposit must be non-negative" }
    }
}
