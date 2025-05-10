package co.getaim.portfolio

import co.getaim.portfolio.enums.RiskType
import co.getaim.portfolio.repository.ActualPortfolioRepository
import co.getaim.portfolio.repository.ModelPortfolioRepository
import co.getaim.security.SecurityFixture.securityResponse
import co.getaim.security.SecurityService
import com.ninjasquad.springmockk.MockkBean
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal.ONE

@SpringBootTest
class PortfolioServiceTest(
    private val portfolioService: PortfolioService,
    private val actualPortfolioRepository: ActualPortfolioRepository,
    @MockkBean val modelPortfolioRepository: ModelPortfolioRepository,
    @MockkBean val securityService: SecurityService,
) : FunSpec({

    // Clean up
    beforeTest {
        actualPortfolioRepository.deleteAll()
    }

    test("예수금이 충분하지 않은경우, AP의 합은 0") {
        coEvery { modelPortfolioRepository.findByRiskType(any()) } returns listOf(
            PortfolioFixture.modelPortfolio.copy(ticker = "AAPL", weight = ONE)
        )
        coEvery { securityService.getSecurityMap(any()) } returns mapOf(
            "AAPL" to securityResponse.copy(price = 100)
        )

        val actualPortfolios = portfolioService.createActualPortfolio(
            accountId = 0L,
            advisoryId = 0L,
            riskType = RiskType.FULL_BALANCE,
            deposit = 0,
        )

        // then
        actualPortfolios.size shouldBe 1
        actualPortfolios.sumOf { it.quantity } shouldBe 0
    }

    test("최대한 예수금을 사용해서 AP를 생성한다.") {
        coEvery { modelPortfolioRepository.findByRiskType(any()) } returns listOf(
            PortfolioFixture.modelPortfolio.copy(ticker = "AAPL", weight = ONE)
        )
        coEvery { securityService.getSecurityMap(any()) } returns mapOf(
            "AAPL" to securityResponse.copy(price = 70) // 주가 70
        )

        val (actualPortfolio) = portfolioService.createActualPortfolio(
            accountId = 0L,
            advisoryId = 0L,
            riskType = RiskType.FULL_BALANCE,
            deposit = 100, // 예수금 100
        )

        // then
        actualPortfolio.quantity shouldBe 1 // 100 / 70 = 1.42 -> floor(1.42) = 1
        actualPortfolio.weight shouldBe "0.7000".toBigDecimal() // 비중 70%
    }

    test("50% 투자인 경우에도 전체 예수금 대비 비중으로 표기한다.") {
        coEvery { modelPortfolioRepository.findByRiskType(any()) } returns listOf(
            PortfolioFixture.modelPortfolio.copy(ticker = "AAPL", weight = ONE)
        )
        coEvery { securityService.getSecurityMap(any()) } returns mapOf(
            "AAPL" to securityResponse.copy(price = 40) // 주가 40
        )

        val (actualPortfolio) = portfolioService.createActualPortfolio(
            accountId = 0L,
            advisoryId = 0L,
            riskType = RiskType.HALF_BALANCE,
            deposit = 100, // 예수금 100
        )

        // then
        actualPortfolio.quantity shouldBe 1 // 50 / 40 = 1.25 -> floor(1.25) = 1
        actualPortfolio.weight shouldBe "0.4000".toBigDecimal() // 비중 40%
    }
})
