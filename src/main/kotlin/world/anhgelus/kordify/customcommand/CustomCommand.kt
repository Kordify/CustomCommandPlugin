package world.anhgelus.kordify.customcommand

import world.anhgelus.kordify.api.Plugin
import world.anhgelus.kordify.common.utils.Logger

class CustomCommand : Plugin() {
    override val logger = object : Logger() {
        override val name = "CustomCommand"
    }

    override fun start() {
        //
    }

    override fun stop() {
        //
    }
}