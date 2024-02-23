package world.anhgelus.kordify.customcommand

import world.anhgelus.kordify.api.Plugin
import world.anhgelus.kordify.common.config.ConfigFile
import world.anhgelus.kordify.common.utils.Logger
import world.anhgelus.kordify.customcommand.exceptions.InvalidConfigException

class CustomCommand : Plugin() {
    override val logger = object : Logger() {
        override val name = "CustomCommand"
    }

    override fun start() {
        val manager = getConfigManager()
        val cmdConf = manager.getConfig("commands")
        logger.info("Parsing commands")
        val commands = parseCmdConf(cmdConf)
        logger.info("Registering commands")
        commands.forEach { it.register() }
    }

    override fun stop() {
        //
    }

    private fun parseCmdConf(cmdConf: ConfigFile): List<GenericCommand> {
        val commandsStar = cmdConf.get("commands") as List<*>? ?: throw InvalidConfigException("Key 'commands' is not set")
        val commands = commandsStar.toList()
        val list = ArrayList<GenericCommand>()
        commands.forEach {
            if (it !is Map<*, *>) {
                throw InvalidConfigException("Inside 'commands' it has to be an object")
            }
            val map = it.toMap()
            val name = map["name"] as String?
                ?: throw InvalidConfigException("Key 'name' is required")
            val description = map["description"] as String?
                ?: throw InvalidConfigException("Key 'description' is required")
            val content = map["content"] as String?
                ?: throw InvalidConfigException("Key 'content' is required")
            val isEmbed = map["is-embed"] as Boolean? ?: false
            val isEphemeral = map["is-ephemeral"] as Boolean? ?: false
            list.add(GenericCommand(name, description, content, isEmbed, isEphemeral))
        }
        return list
    }
}