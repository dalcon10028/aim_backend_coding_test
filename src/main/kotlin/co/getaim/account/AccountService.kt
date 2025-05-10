package co.getaim.account

import co.getaim.account.entity.Account
import co.getaim.account.entity.Transaction
import co.getaim.account.repository.AccountRepository
import co.getaim.account.repository.TransactionRepository
import co.getaim.portfolio.enums.RiskType
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import co.getaim.account.enums.TransactionType.*
import co.getaim.account.dto.*

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val transactionRepository: TransactionRepository,
) {

    suspend fun createAccount(userId: Long, riskType: RiskType): AccountResponse {
        val account = accountRepository.save(Account(userId = userId, riskType = riskType))
        return AccountResponse(
            accountId = account.id!!,
            userId = account.userId,
        )
    }

    @Transactional(readOnly = true)
    suspend fun getAccounts(userId: Long): List<AccountResponse> {
        val accounts = accountRepository.findByUserId(userId)
        return accounts.map {
            AccountResponse(
                accountId = it.id!!,
                userId = it.userId,
            )
        }
    }

    @Transactional(readOnly = true)
    suspend fun getDeposit(accountId: Long): Int = transactionRepository.findDepositByAccountId(accountId)

    @Transactional
    suspend fun deposit(userId: Long, accountId: Long, amount: Int): DepositResponse {
        if (accountRepository.existsByIdAndUserId(accountId, userId).not()) {
            throw IllegalArgumentException("Account is not found, accountId: $accountId, userId: $userId")
        }

        transactionRepository.save(
            Transaction(
                accountId = accountId,
                amount = amount,
                delta = amount,
                type = DEPOSIT
            )
        )

        return DepositResponse(
            accountId = accountId,
            amount = amount,
            deposit = getDeposit(accountId)
        )
    }

    @Transactional
    suspend fun withdraw(userId: Long, accountId: Long, amount: Int): WithdrawResponse {
        if (accountRepository.existsByIdAndUserId(accountId, userId).not()) {
            throw IllegalArgumentException("Account is not found, accountId: $accountId, userId: $userId")
        }

        val deposit = getDeposit(accountId)

        if (deposit < amount) {
            throw IllegalArgumentException("Insufficient deposit, accountId: $accountId, deposit: $deposit, withdrawAmount: $amount")
        }

        transactionRepository.save(
            Transaction(
                accountId = accountId,
                amount = -amount,
                delta = -amount,
                type = WITHDRAW,
            )
        )

        return WithdrawResponse(
            accountId = accountId,
            amount = amount,
            deposit = deposit - amount
        )
    }
}