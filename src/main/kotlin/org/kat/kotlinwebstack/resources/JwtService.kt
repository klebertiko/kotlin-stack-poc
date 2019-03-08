package org.kat.kotlinwebstack.resources

import io.javalin.Context
import io.javalin.security.Role
import org.kat.kotlinwebstack.application.web.auth.Roles

class JwtService {

    fun getUserRole(ctx: Context): Role {
        val jwtToken = getTokenHeader(ctx)
        if (jwtToken.isNullOrBlank()) {
            return Roles.ANYONE
        }

        val userRole = JwtProvider.decodeJWT(jwtToken).getClaim("role").asString()

        return Roles.valueOf(userRole)
    }

    fun getTokenHeader(ctx: Context): String? {
        return ctx.header(header = "Authorization")?.substringAfter(delimiter = "Token")?.trim()
    }
}
