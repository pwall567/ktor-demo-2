package io.kjson.demo2.ports.requires

import io.kjson.demo2.ports.provides.PartyService
import io.kjson.demo2.ports.provides.Properties

interface Config {
    val properties: Properties
    val partyService: PartyService
}
