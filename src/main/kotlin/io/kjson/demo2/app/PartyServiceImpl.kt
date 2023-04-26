package io.kjson.demo2.app

import kotlinx.coroutines.delay

import java.time.Instant
import java.util.UUID

import io.kjson.demo2.ports.provides.PartyService
import io.kjson.demo2.ports.requires.Party
import net.pwall.log.getLogger

class PartyServiceImpl : PartyService {

    override fun getParty(id: String): Party {
        log.info { "Get party $id" }
        return parties.find { it.id == id } ?: throw RuntimeException("Not found - $id")
    }

    override suspend fun getStream(ids: List<String>, consumer: suspend (Party) -> Unit) {
        for (id in ids) {
            delay(3000)
            consumer(getParty(id))
        }
    }

    companion object {

        val log = getLogger()

        private val party1 = Party("1", "Party 1", UUID.randomUUID(), Instant.now())
        private val party2 = Party("2", "Party 2", UUID.randomUUID(), Instant.now())
        private val party3 = Party("3", "Party 3", UUID.randomUUID(), Instant.now())
        private val party4 = Party("4", "Party 4", UUID.randomUUID(), Instant.now())

        val parties = listOf(party1, party2, party3, party4)

    }

}
