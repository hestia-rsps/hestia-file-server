package world.gregs.hestia.file.codec.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Info about whether the client is logged in
 * @param loggedIn Whether the client is in-game
 * @param value Validation value
 */
data class FileServerLoginStatus(val loggedIn: Boolean, val value: Int) : Message