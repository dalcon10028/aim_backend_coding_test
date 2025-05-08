package co.getaim.security.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime

@Table("security")
data class Security(
    @Id val id: Long? = null,
    val name: String,
    val ticker: String,
    val price: Int,
    val deletedAt: LocalDateTime? = null,
    @CreatedDate
    @LastModifiedDate
    val updatedAt: LocalDateTime? = null,
    @CreatedDate
    val createdAt: LocalDateTime? = null,
)
