package world.gregs.hestia.file.codec.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * The client version sent to request handshake
 * @param major The clients major version (667)
 */
data class FileHandshakeMessage(val major: Int) : Message