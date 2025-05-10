package co.getaim.account.entity

import co.getaim.portfolio.enums.RiskType
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("account")
data class Account(
    @Id val id: Long? = null,
    val userId: Long,
    val riskType: RiskType,
    @CreatedDate
    val createdAt: LocalDateTime? = null,
)
