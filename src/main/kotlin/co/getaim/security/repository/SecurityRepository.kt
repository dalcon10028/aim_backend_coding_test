package co.getaim.security.repository

import co.getaim.security.entity.Security
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface SecurityRepository : CoroutineCrudRepository<Security, Long> {
    suspend fun existsByTickerAndDeletedAtIsNull(ticker: String): Boolean

    suspend fun findByTickerAndDeletedAtIsNull(ticker: String): Security?

    suspend fun findByTickerInAndDeletedAtIsNull(ticker: List<String>): List<Security>
}