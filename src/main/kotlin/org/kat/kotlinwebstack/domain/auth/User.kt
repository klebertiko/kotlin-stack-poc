package org.kat.kotlinwebstack.domain.auth

data class UserDTO(val user: User)

data class User(val email: String? = null,
                val token: String? = null,
                val username: String? = null,
                val password: String? = null)

val users = hashMapOf(
    0 to User("user@user.com", "", "user", "user"),
    1 to User("admin@admin.com", "", "admin", "admin")
)