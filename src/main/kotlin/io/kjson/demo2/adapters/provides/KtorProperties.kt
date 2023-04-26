package io.kjson.demo2.adapters.provides

import io.ktor.server.application.ApplicationEnvironment

import io.kjson.demo2.ports.provides.Properties

class KtorProperties(private val environment: ApplicationEnvironment) : Properties {

    override fun get(string: String): String? {
        return environment.config.propertyOrNull(string)?.getString()
    }

}
