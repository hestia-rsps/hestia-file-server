package world.gregs.hestia.file.codec.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Confirms handshake is complete
 * @param value The connection id for validation
 */
data class FileServerConnected(val value: Int) : Message