package co.getaim.common.annotation

import org.springframework.security.core.annotation.AuthenticationPrincipal

@MustBeDocumented
@AuthenticationPrincipal
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class AuthUser
