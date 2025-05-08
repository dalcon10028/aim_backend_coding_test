package co.getaim.account

import co.getaim.account.entity.Transaction
import co.getaim.account.enums.TransactionType.*
import co.getaim.account.repository.AccountRepository
import co.getaim.account.repository.TransactionRepository
import com.ninjasquad.springmockk.MockkBean
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.mockk.coEvery
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AccountServiceTest(
    private val accountService: AccountService,
    @MockkBean val accountRepository: AccountRepository,
    @MockkBean val transactionRepository: TransactionRepository,
) : FunSpec({
    test("존재하지 않는 계좌로 출금시 예외 발생") {
        coEvery { accountRepository.existsByIdAndUserId(accountId = 2L, userId = 1L) } returns false

        val ex = shouldThrow<IllegalArgumentException> {
            accountService.withdraw(userId = 1L, accountId = 2L, amount = 100)
        }
        ex.message shouldBe "Account is not found, accountId: 2, userId: 1"
    }

    test("잔액 부족 출금시 예외 발생") {
        coEvery { accountRepository.existsByIdAndUserId(accountId = 2L, userId = 1L) } returns true
        coEvery { transactionRepository.findByAccountId(2L) } returns listOf(
            Transaction(accountId = 2L, amount = 50, type = DEPOSIT)
        )

        val ex = shouldThrow<IllegalArgumentException> {
            accountService.withdraw(userId = 1L, accountId = 2L, amount = 100)
        }
        ex.message shouldBe "Insufficient balance, accountId: 2, balance: 50, withdrawAmount: 100"
    }
})
