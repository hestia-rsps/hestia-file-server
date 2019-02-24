package world.gregs.hestia.file.codec.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.file.codec.FileServerOpcodes.STATUS_LOGGED_IN
import world.gregs.hestia.file.codec.FileServerOpcodes.STATUS_LOGGED_OUT
import world.gregs.hestia.file.codec.decoders.messages.FileServerLoginStatus

class FileServerLoginStatusDecoder : MessageDecoder<FileServerLoginStatus>(3, STATUS_LOGGED_IN, STATUS_LOGGED_OUT) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): FileServerLoginStatus? {
        val value = packet.readMedium()
        return FileServerLoginStatus(packet.opcode == STATUS_LOGGED_IN, value)
    }

}