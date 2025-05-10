package co.getaim.portfolio.entity

import co.getaim.portfolio.enums.RiskType
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Table("model_portfolio")
data class ModelPortfolio(
    @Id val id: Long? = null,
    val riskType: RiskType,
    val ticker: String,
    val weight: BigDecimal,
    @CreatedDate
    val createdAt: LocalDateTime? = null,
)
