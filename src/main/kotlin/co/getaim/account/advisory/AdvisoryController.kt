package co.getaim.account.advisory

import co.getaim.auth.model.AuthUserDetails
import co.getaim.common.annotation.AuthUser
import org.springframework.web.bind.annotation.*

@RestController
class AdvisoryController(
    private val advisoryService: AdvisoryService,
) {
    @PostMapping("/accounts/{accountId:[0-9]+}/advisory")
    suspend fun create(
        @AuthUser user: AuthUserDetails,
        @PathVariable accountId: Long,
    ) = advisoryService.createAdvisory(accountId)
}