package co.getaim.user.event

import org.springframework.context.ApplicationEvent

data class CreateUserEvent(
    val userId: Long,
) : ApplicationEvent(userId)
