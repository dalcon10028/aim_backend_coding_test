package co.getaim.security

import jakarta.validation.Valid
import co.getaim.security.dto.*
import org.springframework.web.bind.annotation.*

@RestController
class SecurityController(
    private val securityService: SecurityService,
) {
    @PostMapping("/securities")
    suspend fun create(
        @Valid @RequestBody create: SecurityCreate,
    ): SecurityResponse = securityService.create(create)

}