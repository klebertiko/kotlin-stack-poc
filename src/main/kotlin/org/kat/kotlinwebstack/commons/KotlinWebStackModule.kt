package org.kat.kotlinwebstack.commons

import org.kat.kotlinwebstack.application.web.auth.AuthController
import org.kat.kotlinwebstack.application.web.item.ItemController
import org.kat.kotlinwebstack.application.web.message.MessageController
import org.kat.kotlinwebstack.resources.JwtService
import org.kat.kotlinwebstack.resources.persistence.ItemRepository
import org.koin.dsl.module.module

val javalinModule = module {

    single { ItemController() }
    single { AuthController() }
    single { MessageController() }

    single { ItemRepository() }
    single { JwtService() }
}