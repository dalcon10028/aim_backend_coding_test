package co.getaim.auth

import co.getaim.auth.model.AuthUserDetails
import co.getaim.user.UserService
import kotlinx.coroutines.reactor.mono
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Service
class CustomUserDetailsService(
    private val userService: UserService,
) : ReactiveUserDetailsService {
    override fun findByUsername(username: String): Mono<UserDetails?>? =
        mono { userService.getUser(username) ?: throw UsernameNotFoundException("User not found: $username") }
            .map { user -> AuthUserDetails.from(user) }
}