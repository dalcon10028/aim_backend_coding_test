package co.getaim.auth.entity

import co.getaim.auth.enums.LoginHistoryAction
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("login_history")
data class LoginHistory(
    val id: Long? = null,
    val username: String,
    val action: LoginHistoryAction,
    @CreatedDate
    val createdAt: LocalDateTime? = null,
)