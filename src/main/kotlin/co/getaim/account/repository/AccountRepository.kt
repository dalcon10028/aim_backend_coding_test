package co.getaim.account.repository

import co.getaim.account.entity.Account
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface AccountRepository : CoroutineCrudRepository<Account, Long> {
    suspend fun existsByIdAndUserId(accountId: Long, userId: Long): Boolean

    suspend fun findByUserId(userId: Long): List<Account>
}