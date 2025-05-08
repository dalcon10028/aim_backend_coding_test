package co.getaim.account

import co.getaim.user.event.CreateUserEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component

@Component
class AccountEventListener(
    private val accountService: AccountService,
    private val applicationScope: CoroutineScope,
) {
    @EventListener(CreateUserEvent::class)
    fun handleCreateUserEvent(event: CreateUserEvent) {
        applicationScope.launch {
            accountService.createAccount(event.userId)
        }
    }
}