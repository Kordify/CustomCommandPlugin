package world.anhgelus.kordify.customcommand

import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.entities.MessageEmbed
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent
import world.anhgelus.kordify.api.commands.Command
import world.anhgelus.kordify.customcommand.exceptions.InvalidContentException
import java.awt.Color

/**
 * GenericCommand represents a generic command
 * @param name name of the command
 * @param description description of the command
 * @param content content returned by the command
 * @param isEmbed true if the command return an embed (false by default)
 * @param isEphemeral true if the command return an ephemeral message (false by default)
 */
class GenericCommand(
    override val name: String,
    override val description: String,
    val content: String,
    val isEmbed: Boolean = false,
    val isEphemeral: Boolean = false
) : Command() {
    override fun handle(event: SlashCommandInteractionEvent) {
        val reply = if (isEmbed) {
            event.replyEmbeds(generateEmbed())
        } else {
            event.reply(content)
        }
        reply.setEphemeral(isEphemeral).queue()
    }

    private fun generateEmbed(): MessageEmbed {
        val builder = EmbedBuilder()
        /**
         * title: Hello; color: F40C0C; description: hello
         */
        val map = HashMap<String, String>()
        content.split("; ").forEach { info ->
            val data = info.split(": ")
            if (data.size != 2) {
                throw InvalidContentException(InvalidContentException.Reason.REQUIRED_STRUCTURE, "$info is invalid")
            }
            map[data[0]] = data[1]
        }
        val title = map["title"] ?: throw throw InvalidContentException(
            InvalidContentException.Reason.NULL_ARGUMENT,
            "title is not set"
        )
        val color = map["color"] ?: throw throw InvalidContentException(
            InvalidContentException.Reason.NULL_ARGUMENT,
            "color is not set"
        )
        val description = map["description"] ?: throw throw InvalidContentException(
            InvalidContentException.Reason.NULL_ARGUMENT,
            "description is not set"
        )
        builder.setColor(Color(Integer.parseInt(color, 16)))
            .setTitle(title)
            .setDescription(description)
        return builder.build()
    }
}