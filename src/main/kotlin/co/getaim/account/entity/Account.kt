package co.getaim.account.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("account")
data class Account(
    val id: Long? = null,
    val userId: Long,
    val krwDeposit: Int = 0,
    @CreatedDate
    val createdAt: LocalDateTime? = null,
) {
    init {
        require(krwDeposit > 0) { "Deposit must be greater than zero." }
    }
}
