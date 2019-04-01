package world.gregs.hestia.file.handlers

import io.netty.channel.ChannelHandlerContext
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.NetworkConstants
import world.gregs.hestia.core.network.client.Response
import world.gregs.hestia.core.network.clientRespond
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.getSession
import world.gregs.hestia.file.Handshake
import world.gregs.hestia.file.codec.decoders.messages.FileHandshakeMessage
import world.gregs.hestia.file.codec.encoders.messages.GrabKeyStartup

/**
 * Processes the handshake response
 */
class HandshakeHandler : MessageHandler<FileHandshakeMessage> {

    private val logger = LoggerFactory.getLogger(HandshakeHandler::class.java)

    override fun handle(ctx: ChannelHandlerContext, message: FileHandshakeMessage) {
        val (major) = message

        //Check client version
        if (major != NetworkConstants.CLIENT_MAJOR_VERSION) {
            ctx.clientRespond(Response.GAME_UPDATED)
            logger.info("Invalid game version ${ctx.getSession().getHost()} $major")
            return
        }

        //Complete handshake
        ctx.pipeline().get(Handshake::class.java).shake(ctx)
        //Send keys
        ctx.channel().write(GrabKeyStartup())
    }

}