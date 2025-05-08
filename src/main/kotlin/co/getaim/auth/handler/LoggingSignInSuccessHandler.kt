package co.getaim.auth.handler

import co.getaim.auth.entity.LoginHistory
import co.getaim.auth.enums.LoginHistoryAction
import co.getaim.auth.repository.LoginHistoryRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.reactor.mono
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.WebFilterExchange
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class LoggingSignInSuccessHandler(
    private val loginHistoryRepository: LoginHistoryRepository,
) : ServerAuthenticationSuccessHandler {
    private val logger = KotlinLogging.logger {}

    override fun onAuthenticationSuccess(
        webFilterExchange: WebFilterExchange,
        authentication: Authentication,
    ): Mono<Void?>? = mono {
        logger.info { "User ${authentication.name} logged in successfully" }
        loginHistoryRepository.save(
            LoginHistory(
                username = authentication.name,
                action = LoginHistoryAction.LOGIN,
            )
        )
    }.then(webFilterExchange.chain.filter(webFilterExchange.exchange))
}