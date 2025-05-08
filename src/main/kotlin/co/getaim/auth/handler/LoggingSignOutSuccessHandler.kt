package co.getaim.auth.handler

import co.getaim.auth.entity.LoginHistory
import co.getaim.auth.enums.LoginHistoryAction
import co.getaim.auth.repository.LoginHistoryRepository
import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.logout.ServerLogoutSuccessHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class LoggingSignOutSuccessHandler(
    private val loginHistoryRepository: LoginHistoryRepository,
) : ServerLogoutSuccessHandler {

    companion object {
        private const val ANONYMOUS = "anonymous"
    }

    override fun onLogoutSuccess(
        exchange: WebFilterExchange?,
        authentication: Authentication?,
    ): Mono<Void?>? = mono {
        authentication?.let {
            if (it.name == ANONYMOUS) {
                return@mono
            }
            loginHistoryRepository.save(
                LoginHistory(
                    username = it.name,
                    action = LoginHistoryAction.LOGOUT,
                )
            )
        }
    }.then()
}