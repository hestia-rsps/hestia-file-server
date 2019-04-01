package world.gregs.hestia.file.codec.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.file.codec.FileServerOpcodes.HANDSHAKE
import world.gregs.hestia.file.codec.decoders.messages.FileHandshakeMessage

class FileHandshakeDecoder : MessageDecoder<FileHandshakeMessage>(4, HANDSHAKE) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): FileHandshakeMessage? {
        return FileHandshakeMessage(packet.readInt())
    }

}