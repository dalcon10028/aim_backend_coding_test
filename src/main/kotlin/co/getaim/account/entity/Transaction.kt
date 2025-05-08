package co.getaim.account.entity

import co.getaim.account.enums.TransactionType
import co.getaim.account.enums.TransactionType.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("transaction")
data class Transaction(
    val id: Long,
    val accountId: Long,
    val type: TransactionType,
    val amount: Int,
    @CreatedDate
    val createdAt: LocalDateTime,
) {
    init {
        require(amount > 0) { "Amount must be greater than zero." }
    }

    val delta: Int
        get() = when (type) {
            DEPOSIT -> amount
            WITHDRAW -> -amount
        }
}
