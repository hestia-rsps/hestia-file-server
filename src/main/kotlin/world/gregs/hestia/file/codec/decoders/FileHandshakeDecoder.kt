package world.gregs.hestia.file.codec.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.file.codec.FileServerOpcodes.HANDSHAKE
import world.gregs.hestia.file.codec.decoders.messages.FileHandshakeMessage

class FileHandshakeDecoder : MessageDecoder<FileHandshakeMessage>(8, HANDSHAKE) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): FileHandshakeMessage? {
        val major = packet.readInt()
        val minor = packet.readInt()
        return FileHandshakeMessage(major, minor)
    }

}