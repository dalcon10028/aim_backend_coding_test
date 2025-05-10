package co.getaim.security

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import co.getaim.security.dto.*
import co.getaim.security.repository.*
import java.time.LocalDateTime

@Service
class SecurityService(
    private val securityRepository: SecurityRepository,
) {
    @Transactional
    suspend fun create(create: SecurityCreate): SecurityResponse {
        if (securityRepository.existsByTickerAndDeletedAtIsNull(create.ticker)) {
            throw IllegalArgumentException("Ticker already exists for: ${create.ticker}")
        }

        return securityRepository.save(create.toEntity())
            .let { SecurityResponse.from(it) }
    }

    @Transactional
    suspend fun updatePrice(ticker: String, price: Int): SecurityResponse {
        val security = securityRepository.findByTickerAndDeletedAtIsNull(ticker) ?: throw IllegalArgumentException("Security not found for: $ticker")
        return securityRepository.save(security.copy(price = price))
            .let { SecurityResponse.from(it) }
    }

    @Transactional
    suspend fun delete(ticker: String) {
        val security = securityRepository.findByTickerAndDeletedAtIsNull(ticker) ?: throw IllegalArgumentException("Security not found for: $ticker")
        securityRepository.save(security.copy(deletedAt = LocalDateTime.now()))
    }

    @Transactional(readOnly = true)
    suspend fun getSecurityMap(tickers: List<String>): Map<String, SecurityResponse> =
        securityRepository.findByTickerInAndDeletedAtIsNull(tickers)
            .associate { it.ticker to SecurityResponse.from(it) }
}