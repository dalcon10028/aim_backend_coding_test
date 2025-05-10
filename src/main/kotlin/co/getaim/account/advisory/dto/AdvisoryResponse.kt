package co.getaim.account.advisory.dto

import co.getaim.account.advisory.Advisory
import co.getaim.portfolio.dto.ActualPortfolioResponse

data class AdvisoryResponse(
    val advisoryId: Long,
    val accountId: Long,
    val actualPortfolios: List<ActualPortfolioResponse>,
) {
    companion object {
        fun of(advisory: Advisory, actualPortfolios: List<ActualPortfolioResponse>): AdvisoryResponse =
            AdvisoryResponse(
                advisoryId = advisory.id!!,
                accountId = advisory.accountId,
                actualPortfolios = actualPortfolios,
            )
    }
}
