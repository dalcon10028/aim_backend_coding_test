package co.getaim.account.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("account")
data class Account(
    val id: Long? = null,
    val userId: Long,
    @CreatedDate
    val createdAt: LocalDateTime? = null,
)
