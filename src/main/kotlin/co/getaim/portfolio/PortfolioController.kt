package co.getaim.portfolio

import co.getaim.auth.model.AuthUserDetails
import co.getaim.common.annotation.AuthUser
import co.getaim.portfolio.dto.PortfolioUserRiskCreate
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class PortfolioController(
    private val portfolioService: PortfolioService,
) {
    @PostMapping("/portfolio/user/risk")
    suspend fun createPortfolioUserRisk(
        @AuthUser user: AuthUserDetails,
        @Valid @RequestBody create: PortfolioUserRiskCreate,
    ) = portfolioService.createPortfolioUserRisk(user.userId!!, create)
}