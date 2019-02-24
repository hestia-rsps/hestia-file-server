package world.gregs.hestia.file.handlers

import io.netty.channel.ChannelHandlerContext
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.file.codec.decoders.messages.FileServerDisconnect

/**
 * Disconnects the client (used for client testing)
 */
class DisconnectHandler : MessageHandler<FileServerDisconnect> {

    private val logger = LoggerFactory.getLogger(DisconnectHandler::class.java)

    override fun handle(ctx: ChannelHandlerContext, message: FileServerDisconnect) {
        if(message.value != 0) {
            logger.warn("Invalid disconnect id")
        }
        ctx.close()
    }

}