package co.getaim.auth

import co.getaim.auth.dto.SignUpResponse
import co.getaim.user.UserService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val userService: UserService,
    private val passwordEncoder: PasswordEncoder,
) {
    suspend fun signUp(username: String, rawPassword: String): SignUpResponse {
        val user = userService.create(
            username,
            passwordEncoder.encode(rawPassword),
        )
        require(user.id != null) { "User ID should not be null" }
        return SignUpResponse(
            id = user.id,
            username = user.username,
        )
    }
}