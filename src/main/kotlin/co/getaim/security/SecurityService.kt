package co.getaim.security

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import co.getaim.security.dto.*
import co.getaim.security.repository.*

@Service
class SecurityService(
    private val securityRepository: SecurityRepository,
) {
    @Transactional
    suspend fun create(create: SecurityCreate): SecurityResponse {
        if (securityRepository.existsByTicker(create.ticker)) {
            throw IllegalArgumentException("Ticker already exists for: ${create.ticker}")
        }

        return securityRepository.save(create.toEntity())
            .let { SecurityResponse.from(it) }
    }

    @Transactional
    suspend fun updatePrice(ticker: String, price: Int): SecurityResponse {
        val security = securityRepository.findByTicker(ticker) ?: throw IllegalArgumentException("Security not found for: $ticker")
        return securityRepository.save(security.copy(price = price))
            .let { SecurityResponse.from(it) }
    }
}