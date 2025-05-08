package co.getaim.config

import co.getaim.auth.handler.LoggingSignInSuccessHandler
import co.getaim.auth.handler.LoggingSignOutSuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers
import org.springframework.security.config.web.server.invoke

@Configuration
@EnableWebFluxSecurity
class WebSecurityConfig(
    private val loggingSignInSuccessHandler: LoggingSignInSuccessHandler,
    private val loggingSignOutSuccessHandler: LoggingSignOutSuccessHandler,
) {
    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http {
            authorizeExchange {
                authorize(pathMatchers("/auth/**"), permitAll)
                authorize(anyExchange, authenticated)
            }
            formLogin {
                authenticationSuccessHandler = loggingSignInSuccessHandler
            }
            httpBasic { disable() }
            csrf { disable() }
            cors { disable() }
            logout {
                logoutUrl = "/auth/signout"
                logoutSuccessHandler = loggingSignOutSuccessHandler
            }
        }
    }
}
