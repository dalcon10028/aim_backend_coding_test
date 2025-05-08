package co.getaim.portfolio

import co.getaim.portfolio.dto.PortfolioUserRiskCreate
import co.getaim.portfolio.dto.PortfolioUserRiskResponse
import co.getaim.portfolio.entity.PortfolioUserRisk
import co.getaim.portfolio.repository.PortfolioUserRiskRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PortfolioService(
    private val portfolioUserRiskRepository: PortfolioUserRiskRepository,
) {

    /**
     * 포트폴리오 위험도 선택
     */
    @Transactional
    suspend fun createPortfolioUserRisk(userId: Long, create: PortfolioUserRiskCreate) {
        if (portfolioUserRiskRepository.existsByUserId(userId)) {
            throw IllegalArgumentException("User risk already exists for userId: $userId")
        }

        return portfolioUserRiskRepository.save(
            PortfolioUserRisk(
                userId = userId,
                riskType = create.riskType,
            )
        ).let { PortfolioUserRiskResponse.from(it) }
    }

}