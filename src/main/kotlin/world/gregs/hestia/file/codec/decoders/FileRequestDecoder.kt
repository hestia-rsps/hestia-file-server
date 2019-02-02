package world.gregs.hestia.file.codec.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.file.codec.FileServerOpcodes.FILE_REQUEST
import world.gregs.hestia.file.codec.FileServerOpcodes.PRIORITY_FILE_REQUEST
import world.gregs.hestia.file.codec.decoders.messages.FileRequest

class FileRequestDecoder : MessageDecoder<FileRequest>(5, FILE_REQUEST, PRIORITY_FILE_REQUEST) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): FileRequest? {
        val index = packet.readUnsignedByte()
        val archive = packet.readInt()
        return FileRequest(index, archive, packet.opcode == 1)
    }

}