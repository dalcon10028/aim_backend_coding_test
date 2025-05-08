package co.getaim.account.repository

import co.getaim.account.entity.Account
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface AccountRepository : CoroutineCrudRepository<Account, Long>