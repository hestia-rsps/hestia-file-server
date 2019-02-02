package world.gregs.hestia.file.codec.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.file.codec.FileServerOpcodes.DISCONNECT
import world.gregs.hestia.file.codec.decoders.messages.FileServerDisconnect

class FileServerDisconnectDecoder : MessageDecoder<FileServerDisconnect>(3, DISCONNECT) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): FileServerDisconnect? {
        val value = packet.readMedium()
        return FileServerDisconnect(value)
    }

}