package co.getaim.security.repository

import co.getaim.security.entity.Security
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface SecurityRepository : CoroutineCrudRepository<Security, Long> {
    suspend fun existsByTicker(ticker: String): Boolean

    suspend fun findByTicker(ticker: String): Security?
}