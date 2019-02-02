package world.gregs.hestia.file.handlers

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.cache.Cache
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.file.codec.decoders.messages.FileRequest
import world.gregs.hestia.file.codec.encoders.messages.CacheArchiveFile
import world.gregs.hestia.file.handlers.EncryptionKeyHandler.Companion.encryptionKey

/**
 * Processes a file request (priority or not)
 */
class FileRequestHandler(private val cache: Cache) : MessageHandler<FileRequest> {

    override fun handle(ctx: ChannelHandlerContext, message: FileRequest) {
        val (index, archive, priority) = message

        //Check index and archive exist
        if (archive < 0) {
            return
        }
        if (index != 255) {
            if (cache.indexCount() <= index || !cache.getIndex(index).archiveExists(archive)) {
                return
            }
        } else if (archive != 255) {
            if (cache.indexCount() <= archive) {
                return
            }
        }
        //Retrieve cache data
        val data = cache.getArchive(index, archive) ?: return
        //Retrieve encryption key
        val encryption = if (index == 255 && archive == 255 || !ctx.channel().hasAttr(encryptionKey)) 0 else ctx.channel().attr(encryptionKey).get()
        //Read compression key
        val compression = data[0].toInt() and 0xff
        //Read data length
        val length = ((data[1].toInt() and 0xff shl 24) + (data[2].toInt() and 0xff shl 16) + (data[3].toInt() and 0xff shl 8) + (data[4].toInt() and 0xff))
        //Mark as non-priority
        var settings = compression
        if (!priority) {
            settings = settings or 0x80
        }
        //Calculate uncompress size
        val size = if (compression == 0) length else length + 4
        //Send file
        ctx.channel().write(CacheArchiveFile(index, archive, settings, length, data, size, encryption))
    }

}