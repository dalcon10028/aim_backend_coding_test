package co.getaim.portfolio.repository

import co.getaim.portfolio.entity.PortfolioUserRisk
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface PortfolioUserRiskRepository : CoroutineCrudRepository<PortfolioUserRisk, Long> {
    suspend fun existsByUserId(userId: Long): Boolean
}