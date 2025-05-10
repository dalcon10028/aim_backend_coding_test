package co.getaim.account.advisory

import co.getaim.account.AccountService
import co.getaim.account.advisory.dto.AdvisoryResponse
import co.getaim.portfolio.PortfolioService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AdvisoryService(
    private val advisoryRepository: AdvisoryRepository,
    private val portfolioService: PortfolioService,
    private val accountService: AccountService,
) {
    @Transactional
    suspend fun createAdvisory(accountId: Long): AdvisoryResponse {
        val account = accountService.getAccount(accountId)
        val advisory = advisoryRepository.save(
            Advisory(
                accountId = accountId,
            )
        )
        require(advisory.id != null) { "Advisory ID is null" }
        val actualPortfolios = portfolioService.createActualPortfolio(
            accountId,
            advisory.id,
            account.riskType,
            deposit = accountService.getDeposit(accountId)
        )

        return advisoryRepository.save(advisory)
            .let { AdvisoryResponse.of(it, actualPortfolios) }
    }
}