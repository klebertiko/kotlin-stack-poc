package org.kat.kotlinwebstack.application.web.auth

import io.javalin.Context
import org.kat.kotlinwebstack.domain.auth.UserDTO
import org.kat.kotlinwebstack.domain.auth.users
import org.kat.kotlinwebstack.resources.JwtProvider

class AuthController {

    fun login(ctx: Context): UserDTO {
        val userRequest = ctx.validatedBody<UserDTO>()
                .check({ !it.user.username.isNullOrBlank() })
                .check({ !it.user.password.isNullOrBlank() })
                .check({ !it.user.username.equals(users[0]!!.username) })
                .check({ !it.user.password.equals(users[0]!!.password) })
                .getOrThrow()
        return UserDTO(
            userRequest.user.copy(
                token = JwtProvider.createJWT(
                    userRequest.user,
                    Roles.AUTHENTICATED
                )
            )
        )
    }
}