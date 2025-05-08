package co.getaim.security

import jakarta.validation.Valid
import co.getaim.security.dto.*
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class SecurityController(
    private val securityService: SecurityService,
) {
    @PostMapping("/securities")
    suspend fun create(
        @Valid @RequestBody create: SecurityCreate,
    ): SecurityResponse = securityService.create(create)

    @PutMapping("/securities/{ticker:[0-9a-zA-Z]+}/price")
    suspend fun updatePrice(
        @PathVariable ticker: String,
        @Valid @RequestBody update: SecurityPriceUpdate,
    ): SecurityResponse = securityService.updatePrice(ticker, update.price)

    @DeleteMapping("/securities/{ticker:[0-9a-zA-Z]+}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun delete(
        @PathVariable ticker: String,
    ) = securityService.delete(ticker)
}