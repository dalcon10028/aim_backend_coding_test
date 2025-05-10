package co.getaim.account.advisory

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("advisory")
data class Advisory(
    @Id val id: Long? = null,
    val accountId: Long,
    @CreatedDate
    val createdAt: LocalDateTime? = null,
)
