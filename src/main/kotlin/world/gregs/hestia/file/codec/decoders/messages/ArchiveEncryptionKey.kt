package world.gregs.hestia.file.codec.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * The encryption key the client is using
 * @param value Key value
 * @param mark Validation value
 */
data class ArchiveEncryptionKey(val value: Int, val mark: Int) : Message