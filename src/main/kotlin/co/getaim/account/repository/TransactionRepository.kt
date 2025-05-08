package co.getaim.account.repository

import co.getaim.account.entity.Transaction
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface TransactionRepository : CoroutineCrudRepository<Transaction, Long>