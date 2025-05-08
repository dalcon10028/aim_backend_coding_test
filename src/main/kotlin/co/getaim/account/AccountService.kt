package co.getaim.account

import co.getaim.account.dto.AccountResponse
import co.getaim.account.dto.DepositResponse
import co.getaim.account.dto.WithdrawResponse
import co.getaim.account.entity.Account
import co.getaim.account.entity.Transaction
import co.getaim.account.enums.TransactionType.*
import co.getaim.account.repository.AccountRepository
import co.getaim.account.repository.TransactionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
) {
    suspend fun createAccount(userId: Long): Account {
        val account = Account(userId = userId)
        return accountRepository.save(account)
    }

    @Transactional(readOnly = true)
    suspend fun getAccounts(userId: Long): List<AccountResponse> {
        val accounts = accountRepository.findByUserId(userId)
        val balanceMap = transactionRepository.findByAccountIdIn(accounts.map { it.id!! })
            .groupBy { it.accountId }
            .mapValues { it.value.sumOf { transaction -> transaction.delta } }
        return accounts.map {
            AccountResponse(
                accountId = it.id!!,
                userId = it.userId,
                balance = balanceMap[it.id] ?: 0,
            )
        }
    }

    @Transactional
    suspend fun deposit(userId: Long, accountId: Long, amount: Int): DepositResponse {
        if (accountRepository.existsByIdAndUserId(accountId, userId).not()) {
            throw IllegalArgumentException("Account is not found, accountId: $accountId, userId: $userId")
        }

        transactionRepository.save(
            Transaction(
                accountId = accountId,
                amount = amount,
                type = DEPOSIT
            )
        )

        return DepositResponse(
            accountId = accountId,
            amount = amount,
            balance = transactionRepository.findByAccountId(accountId).sumOf { it.delta }
        )
    }

    @Transactional
    suspend fun withdraw(userId: Long, accountId: Long, amount: Int): WithdrawResponse {
        if (accountRepository.existsByIdAndUserId(accountId, userId).not()) {
            throw IllegalArgumentException("Account is not found, accountId: $accountId, userId: $userId")
        }

        val balance = transactionRepository.findByAccountId(accountId).sumOf { it.delta }

        if (balance < amount) {
            throw IllegalArgumentException("Insufficient balance, accountId: $accountId, balance: $balance, withdrawAmount: $amount")
        }

        transactionRepository.save(
            Transaction(
                accountId = accountId,
                amount = -amount,
                type = WITHDRAW
            )
        )

        return WithdrawResponse(
            accountId = accountId,
            amount = amount,
            balance = balance - amount
        )
    }
}