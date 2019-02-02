package world.gregs.hestia.file.handlers

import io.netty.channel.ChannelHandlerContext
import org.slf4j.LoggerFactory
import world.gregs.hestia.core.network.client.Response
import world.gregs.hestia.core.network.clientRespond
import world.gregs.hestia.core.network.codec.message.MessageHandler
import world.gregs.hestia.core.network.getSession
import world.gregs.hestia.file.codec.decoders.messages.FileServerLoginStatus

/**
 * Message from client telling if user is logged-in or not
 */
class LoginStatusHandler : MessageHandler<FileServerLoginStatus> {

    private val logger = LoggerFactory.getLogger(LoginStatusHandler::class.java)

    override fun handle(ctx: ChannelHandlerContext, message: FileServerLoginStatus) {
        val (login, value) = message
        if (value != 0) {
            ctx.clientRespond(Response.BAD_SESSION_ID)
            logger.warn("Invalid login id ${ctx.getSession().getHost()} $value")
            return
        }

        logger.info("Client is ${if(login) "logged in" else "logged out"} ${ctx.getSession().getHost()}")
    }

}