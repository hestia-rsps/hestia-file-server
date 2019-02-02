package world.gregs.hestia.file.handlers

import io.netty.channel.ChannelHandlerContext
import io.netty.util.AttributeKey
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.file.codec.decoders.messages.ArchiveEncryptionKey

/**
 * Encryption value sent by the client if non-zero
 */
class EncryptionKeyHandler : MessageHandler<ArchiveEncryptionKey> {

    private val logger = LoggerFactory.getLogger(EncryptionKeyHandler::class.java)

    override fun handle(ctx: ChannelHandlerContext, message: ArchiveEncryptionKey) {
        val (value, mark) = message
        ctx.channel().attr(encryptionKey).set(value)
        if (mark != 0) {
            logger.info("Invalid decryption packet $mark")
            ctx.close()
        }
    }

    companion object {
        val encryptionKey = AttributeKey.valueOf<Int>("encryption.key")!!
    }

}