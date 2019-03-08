package org.kat.kotlinwebstack.common

import org.kat.kotlinwebstack.application.web.auth.AuthController
import org.kat.kotlinwebstack.application.web.item.ItemController
import org.kat.kotlinwebstack.resources.JwtService
import org.koin.dsl.module.module

val javalinModule = module {

    single { ItemController() }
    single { AuthController() }
    single { JwtService() }
}