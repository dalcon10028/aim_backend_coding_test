package co.getaim.auth.repository

import co.getaim.auth.entity.LoginHistory
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface LoginHistoryRepository : CoroutineCrudRepository<LoginHistory, Long>