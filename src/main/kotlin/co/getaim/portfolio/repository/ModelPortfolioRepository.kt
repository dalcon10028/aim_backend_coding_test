package co.getaim.portfolio.repository

import co.getaim.portfolio.entity.ModelPortfolio
import co.getaim.portfolio.enums.RiskType
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface ModelPortfolioRepository : CoroutineCrudRepository<ModelPortfolio, Long> {
    suspend fun findByRiskType(riskType: RiskType): List<ModelPortfolio>
}