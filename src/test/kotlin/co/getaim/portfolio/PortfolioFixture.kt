package co.getaim.portfolio

import co.getaim.portfolio.entity.ActualPortfolio
import co.getaim.portfolio.entity.ModelPortfolio
import co.getaim.portfolio.enums.RiskType
import java.math.BigDecimal.ONE

object PortfolioFixture {
    val modelPortfolio = ModelPortfolio(
        id = 1L,
        riskType = RiskType.FULL_BALANCE,
        ticker = "AAPL",
        weight = ONE
    )

    val actualPortfolio = ActualPortfolio(
        accountId = 1L,
        riskType = RiskType.FULL_BALANCE,
        advisoryId = 1L,
        ticker = "AAPL",
        weight = ONE,
        quantity = 10,
        price = 100,
    )
}