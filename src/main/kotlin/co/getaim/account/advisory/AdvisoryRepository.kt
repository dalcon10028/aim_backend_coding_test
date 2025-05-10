package co.getaim.account.advisory

import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface AdvisoryRepository : CoroutineCrudRepository<Advisory, Long>