package co.getaim.portfolio

import co.getaim.portfolio.dto.ActualPortfolioResponse
import co.getaim.portfolio.entity.ActualPortfolio
import co.getaim.portfolio.entity.ModelPortfolio
import co.getaim.portfolio.enums.RiskType
import org.springframework.stereotype.Service
import co.getaim.portfolio.repository.*
import co.getaim.security.SecurityService
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal.ONE
import java.math.BigDecimal.ZERO
import java.math.RoundingMode

@Service
class PortfolioService(
    private val modelPortfolioRepository: ModelPortfolioRepository,
    private val actualPortfolioRepository: ActualPortfolioRepository,
    private val securityService: SecurityService,
) {
    suspend fun getModelPortfolios(riskType: RiskType): List<ModelPortfolio> {
        val portfolios = modelPortfolioRepository.findByRiskType(riskType)
        if (portfolios.sumOf { it.weight }.stripTrailingZeros() != ONE) {
            throw IllegalArgumentException("Model portfolio weights must sum to 100%")
        }
        return portfolios
    }

    @Transactional
    suspend fun createActualPortfolio(
        accountId: Long,
        advisoryId: Long,
        riskType: RiskType,
        deposit: Int,
    ): List<ActualPortfolioResponse> {
        val modelPortfolios = getModelPortfolios(riskType)
        val securityMap = securityService.getSecurityMap(modelPortfolios.map { it.ticker })
        // AP 생성 가능 금액
        val availableDeposit = when (riskType) {
            RiskType.FULL_BALANCE -> deposit
            RiskType.HALF_BALANCE -> deposit / 2
        }.toBigDecimal()

        val actualPortfolios = modelPortfolios
            // 1. 모델 포트폴리오를 큰 비중순으로 정렬
            .sortedByDescending { it.weight }
            // 2. 예수금에 비중을 초과하지 않는 AP를 생성
            .map { mp ->
                val amount = availableDeposit * mp.weight
                val security = securityMap[mp.ticker] ?: throw IllegalArgumentException("Security not found for ticker: ${mp.ticker}")
                val quantity = amount.divide(security.price.toBigDecimal(), 0, RoundingMode.FLOOR).toInt()
                // 비중은 위험도 상관없이 전체 예수금 대비 비율로 계산
                val weight = if (quantity == 0) ZERO // 수량이 0인 경우 비중을 0으로 설정
                else (security.price.toBigDecimal() * quantity.toBigDecimal()).divide(deposit.toBigDecimal(), 4, RoundingMode.FLOOR)
                ActualPortfolio(
                    accountId = accountId,
                    riskType = riskType,
                    advisoryId = advisoryId,
                    ticker = mp.ticker,
                    quantity = quantity,
                    price = security.price,
                    weight = weight,
                )
            }

        return actualPortfolioRepository.saveAll(actualPortfolios)
            .map { ActualPortfolioResponse.from(it) }
            .toList()
    }
}