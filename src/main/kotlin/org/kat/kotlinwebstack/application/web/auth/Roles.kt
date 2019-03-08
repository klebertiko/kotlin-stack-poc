package org.kat.kotlinwebstack.application.web.auth

import io.javalin.security.Role

internal enum class Roles : Role {
    ANYONE, AUTHENTICATED
}