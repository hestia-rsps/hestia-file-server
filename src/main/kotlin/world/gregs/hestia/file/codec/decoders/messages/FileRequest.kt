package world.gregs.hestia.file.codec.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Archive file requested by the client
 * @param index The cache index
 * @param archive The archive id
 * @param priority Whether file is a priority
 */
data class FileRequest(val index: Int, val archive: Int, val priority: Boolean) : Message