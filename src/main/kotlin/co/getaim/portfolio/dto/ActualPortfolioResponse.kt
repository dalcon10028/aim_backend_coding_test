package co.getaim.portfolio.dto

import co.getaim.portfolio.entity.ActualPortfolio
import co.getaim.portfolio.enums.RiskType
import java.math.BigDecimal

data class ActualPortfolioResponse(
    val riskType: RiskType,
    val ticker: String,
    val weight: BigDecimal,
    val quantity: Int,
    val price: Int,
) {
    companion object {
        fun from(actualPortfolio: ActualPortfolio): ActualPortfolioResponse =
            ActualPortfolioResponse(
                riskType = actualPortfolio.riskType,
                ticker = actualPortfolio.ticker,
                weight = actualPortfolio.weight,
                quantity = actualPortfolio.quantity,
                price = actualPortfolio.price,
            )
    }
}
