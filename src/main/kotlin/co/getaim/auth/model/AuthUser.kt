package co.getaim.auth.model

import co.getaim.user.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

data class AuthUser(
    val uid: Long? = null,
    private val username: String,
    private val password: String,
) : UserDetails {
    override fun getAuthorities(): Collection<GrantedAuthority?>? = emptyList()

    override fun getPassword(): String = password

    override fun getUsername(): String = username

    companion object {
        fun from(user: User): AuthUser {
            return AuthUser(
                uid = user.id,
                username = user.username,
                password = user.password,
            )
        }
    }
}
