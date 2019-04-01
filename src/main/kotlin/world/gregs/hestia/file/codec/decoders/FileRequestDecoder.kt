package world.gregs.hestia.file.codec.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.DataType
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.file.codec.FileServerOpcodes.FILE_REQUEST
import world.gregs.hestia.file.codec.FileServerOpcodes.PRIORITY_FILE_REQUEST
import world.gregs.hestia.file.codec.decoders.messages.FileRequest

class FileRequestDecoder : MessageDecoder<FileRequest>(3, FILE_REQUEST, PRIORITY_FILE_REQUEST) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): FileRequest? {
        return FileRequest(packet.readUnsigned(DataType.MEDIUM), packet.opcode == 1)
    }

}