package world.gregs.hestia.file.handlers

import io.netty.channel.ChannelHandlerContext
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.client.Response
import world.gregs.hestia.core.network.clientRespond
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.getSession
import world.gregs.hestia.file.codec.decoders.messages.FileServerConnected

/**
 * Is sent by the client once the handshake is complete
 */
class ConnectedHandler : MessageHandler<FileServerConnected> {

    private val logger = LoggerFactory.getLogger(ConnectedHandler::class.java)

    override fun handle(ctx: ChannelHandlerContext, message: FileServerConnected) {
        if (message.value != 3) {
            ctx.clientRespond(Response.BAD_SESSION_ID)
            logger.warn("Invalid connection id ${ctx.getSession().getHost()} ${message.value}")
            return
        }

        logger.info("Connection complete ${ctx.getSession().getHost()}")
    }

}