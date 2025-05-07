package co.getaim.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authorization.method.AuthorizationAdvisorProxyFactory.withDefaults
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers.pathMatchers
import org.springframework.security.config.web.server.invoke

@Configuration
@EnableWebFluxSecurity
class WebSecurityConfig{
    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {
        return http {
            authorizeExchange {
                authorize(pathMatchers("/auth/**"), permitAll)
                authorize(anyExchange, authenticated)
            }
            formLogin { withDefaults() }
            httpBasic { disable() }
            csrf { disable() }
            cors { disable() }
        }
    }
}
