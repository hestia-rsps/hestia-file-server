package world.gregs.hestia.file.codec.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.file.codec.FileServerOpcodes.CONNECTED
import world.gregs.hestia.file.codec.decoders.messages.FileServerConnected

class FileServerConnectedDecoder : MessageDecoder<FileServerConnected>(3, CONNECTED) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): FileServerConnected? {
        val value = packet.readMedium()
        return FileServerConnected(value)
    }

}