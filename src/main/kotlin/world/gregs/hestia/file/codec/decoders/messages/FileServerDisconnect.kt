package world.gregs.hestia.file.codec.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Request to terminate network connection
 * Note: Only used for client testing
 * @param value Validation value
 */
data class FileServerDisconnect(val value: Int) : Message