package co.getaim.user

import co.getaim.user.event.CreateUserEvent
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val applicationEventPublisher: ApplicationEventPublisher,
) {
    suspend fun getUser(username: String): User? = userRepository.findByUsername(username)

    suspend fun create(username: String, password: String): User {
        if (userRepository.existsByUsername(username)) {
            throw IllegalArgumentException("User with username $username already exists")
        }

        val user = userRepository.save(User(username = username, password = password))
        require(user.id != null) { "User ID should not be null" }
        applicationEventPublisher.publishEvent(CreateUserEvent(userId = user.id)) // 별도 트랜잭션 처리, 에러나도 롤백 x
        return user
    }
}