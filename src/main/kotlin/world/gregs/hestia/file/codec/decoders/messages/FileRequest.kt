package world.gregs.hestia.file.codec.decoders.messages

import world.gregs.hestia.core.network.codec.message.Message

/**
 * Archive file requested by the client
 * @param hash The cache index and archive id combined
 * @param priority Whether file is a priority
 */
data class FileRequest(val hash: Long, val priority: Boolean) : Message