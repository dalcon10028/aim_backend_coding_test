package co.getaim.portfolio.entity

import co.getaim.portfolio.enums.RiskType
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Table("actual_portfolio")
data class ActualPortfolio(
    @Id val id: Long? = null,
    val accountId: Long,
    val riskType: RiskType,
    val advisoryId: Long,
    val ticker: String,
    val weight: BigDecimal,
    val quantity: Int,
    val price: Int,
    @CreatedDate
    val createdAt: LocalDateTime? = null,
)
