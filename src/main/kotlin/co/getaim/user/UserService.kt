package co.getaim.user

import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    suspend fun getUser(username: String): User? = userRepository.findByUsername(username)

    suspend fun create(username: String, password: String): User {
        if (userRepository.existsByUsername(username)) {
            throw IllegalArgumentException("User with username $username already exists")
        }
        return userRepository.save(User(username = username, password = password))
    }
}