package co.getaim.account

import co.getaim.auth.model.AuthUserDetails
import co.getaim.common.annotation.AuthUser
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import co.getaim.account.dto.*

@RestController
class AccountController(
    private val accountService: AccountService,
) {
    @GetMapping("/accounts")
    suspend fun getAccounts(
        @AuthUser user: AuthUserDetails,
    ) = accountService.getAccounts(user.userId!!)

    @PostMapping("/accounts/{accountId:[0-9]+}/deposit")
    suspend fun deposit(
        @AuthUser user: AuthUserDetails,
        @PathVariable accountId: Long,
        @Valid @RequestBody depositRequest: DepositRequest,
    ) = accountService.deposit(user.userId!!, accountId, depositRequest.amount)

    @PostMapping("/accounts/{accountId:[0-9]+}/withdraw")
    suspend fun withdraw(
        @AuthUser user: AuthUserDetails,
        @PathVariable accountId: Long,
        @Valid @RequestBody depositRequest: WithdrawRequest,
    ) = accountService.withdraw(user.userId!!, accountId, depositRequest.amount)
}