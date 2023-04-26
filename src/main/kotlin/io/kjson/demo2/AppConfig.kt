package io.kjson.demo2

import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.routing.routing

import io.kjson.demo2.adapters.provides.KtorProperties
import io.kjson.demo2.adapters.provides.appRouting
import io.kjson.demo2.app.PartyServiceImpl
import io.kjson.demo2.ports.provides.PartyService
import io.kjson.demo2.ports.provides.Properties
import io.kjson.demo2.ports.requires.Config
import io.kjson.ktor.kjson
import net.pwall.log.getLogger

object AppConfig : Config {

    override val properties: Properties
        get() = ktorProperties

    private lateinit var ktorProperties: KtorProperties

    override val partyService: PartyService = PartyServiceImpl()

    val log = getLogger()

    fun Application.module() {

        ktorProperties = KtorProperties(environment)

        install(ContentNegotiation) {
            kjson {
                streamOutput = true
            }
        }

        routing {
            appRouting(AppConfig)
        }

        log.info { properties["app.startMessage"] ?: "Starting..." }

    }

}
