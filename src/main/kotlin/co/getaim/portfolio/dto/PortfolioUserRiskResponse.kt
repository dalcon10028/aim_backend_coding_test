package co.getaim.portfolio.dto

import co.getaim.portfolio.entity.PortfolioUserRisk
import co.getaim.portfolio.enums.RiskType

data class PortfolioUserRiskResponse(
    val userId: Long,
    val riskType: RiskType,
) {
    companion object {
        fun from(portfolioUserRisk: PortfolioUserRisk) = PortfolioUserRiskResponse(
            userId = portfolioUserRisk.userId,
            riskType = portfolioUserRisk.riskType,
        )
    }
}
