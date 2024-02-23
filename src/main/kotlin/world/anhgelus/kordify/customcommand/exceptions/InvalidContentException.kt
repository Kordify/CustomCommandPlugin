package world.anhgelus.kordify.customcommand.exceptions

class InvalidContentException(
    val reason: Reason,
    val content: String
) : Exception() {
    override val message: String
        get() = getMessage()

    private fun getMessage(): String {
        return "${reason.error}: $content"
    }

    enum class Reason(
        val error: String
    ) {
        REQUIRED_STRUCTURE("The content field does not follow the required structure"),
        NULL_ARGUMENT("One of the required argument is null")
    }
}