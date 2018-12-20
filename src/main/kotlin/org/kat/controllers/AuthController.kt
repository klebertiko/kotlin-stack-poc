package org.kat.controllers

import io.javalin.Context
import org.kat.Roles
import org.kat.User
import org.kat.UserDTO
import org.kat.users
import org.kat.util.JwtProvider

class AuthController(private val data: Map<Int, User>) {

    fun login(ctx: Context): UserDTO {
        val userRequest = ctx.validatedBody<UserDTO>()
                .check({ !it.user.username.isNullOrBlank() })
                .check({ !it.user.password.isNullOrBlank() })
                .check({ !it.user.username.equals(users[0]!!.username) })
                .check({ !it.user.password.equals(users[0]!!.password) })
                .getOrThrow()
        return UserDTO(userRequest.user.copy(token = JwtProvider.createJWT(userRequest.user, Roles.AUTHENTICATED)))
    }
}