package io.kjson.demo2.ports.provides

import io.kjson.demo2.ports.requires.Party

interface PartyService {

    fun getParty(id: String): Party
    suspend fun getList(ids: List<String>): List<Party>
    suspend fun getStream(ids: List<String>, consumer: suspend (Party) -> Unit)

}
