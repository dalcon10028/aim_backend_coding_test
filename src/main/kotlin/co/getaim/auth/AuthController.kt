package co.getaim.auth

import co.getaim.auth.dto.SignUpRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/auth/signup")
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun signUp(
        @Valid @RequestBody request: SignUpRequest,
    ) = authService.signUp(request.username, request.password)
}