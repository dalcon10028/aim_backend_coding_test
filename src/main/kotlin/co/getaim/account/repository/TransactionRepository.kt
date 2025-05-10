package co.getaim.account.repository

import co.getaim.account.entity.Transaction
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TransactionRepository : CoroutineCrudRepository<Transaction, Long> {
    suspend fun findByAccountId(accountId: Long): List<Transaction>

    suspend fun findByAccountIdIn(accountIds: List<Long>): List<Transaction>

    @Query("""
        SELECT COALESCE(SUM(delta), 0)
        FROM transaction
        WHERE account_id = :accountId
    """)
    suspend fun findDepositByAccountId(accountId: Long): Int
}