package co.getaim.account

import co.getaim.account.entity.Account
import co.getaim.account.repository.AccountRepository
import co.getaim.account.repository.TransactionRepository
import org.springframework.stereotype.Service

@Service
class AccountService(
    private val accountRepository: AccountRepository,
    private val transactionService: TransactionRepository,
) {
    suspend fun createAccount(userId: Long): Account {
        val account = Account(userId = userId)
        return accountRepository.save(account)
    }
}