package io.kjson.demo2.adapters.provides

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post

import io.kstuff.log.getLogger

import io.kjson.demo2.ports.requires.Config
import io.kjson.ktor.respondLines

@OptIn(ExperimentalCoroutinesApi::class)
fun Routing.appRouting(config: Config) {

    val log = getLogger()

    get("/party/single/{id}") {
        val id = call.parameters["id"] ?: throw IllegalArgumentException("No id")
        log.info { "GET /party/single/$id" }
        val party = config.partyService.getParty(id)
        call.respond(party)
    }

    get("/party/list/{ids}") {
        val ids = call.parameters["ids"] ?: throw IllegalArgumentException("No ids")
        log.info { "GET /party/list/$ids" }
        val list = config.partyService.getList(ids.split('.'))
        call.respond(list)
    }

    get("/party/channel/{ids}") {
        val ids = call.parameters["ids"] ?: throw IllegalArgumentException("No ids")
        log.info { "GET /party/channel/$ids" }
        val channel = call.application.produce {
            config.partyService.getStream(ids.split('.')) {
                log.info { "Sending ${it.id}" }
                send(it)
            }
        }
        call.respond(channel)
    }

    get("/party/flow/{ids}") {
        val ids = call.parameters["ids"] ?: throw IllegalArgumentException("No ids")
        log.info { "GET /party/flow/$ids" }
        val flow = flow {
            config.partyService.getStream(ids.split('.')) {
                log.info { "Sending ${it.id}" }
                emit(it)
            }
        }
        call.respond(flow)
    }

    post("/party/post") {
        log.info { "POST /party/post" }
        val inputFlow = call.receive<Flow<String>>()
        val outputFlow = flow {
            inputFlow.collect {
                val party = config.partyService.getParty(it)
                emit(party)
            }
        }
        call.respond(outputFlow)
    }

    get("/party/lines/{ids}") {
        val ids = call.parameters["ids"] ?: throw IllegalArgumentException("No ids")
        log.info { "GET /party/lines/$ids" }
        val flow = flow {
            config.partyService.getStream(ids.split('.')) {
                log.info { "Sending ${it.id}" }
                emit(it)
            }
        }
        call.respondLines(flow)
    }

}
