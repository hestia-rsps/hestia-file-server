package world.gregs.hestia.file.codec.decoders

import io.netty.channel.ChannelHandlerContext
import world.gregs.hestia.core.network.codec.message.MessageDecoder
import world.gregs.hestia.core.network.codec.packet.Packet
import world.gregs.hestia.file.codec.FileServerOpcodes.ENCRYPTION
import world.gregs.hestia.file.codec.decoders.messages.ArchiveEncryptionKey

class EncryptionKeyDecoder : MessageDecoder<ArchiveEncryptionKey>(3, ENCRYPTION) {

    override fun decode(ctx: ChannelHandlerContext, packet: Packet): ArchiveEncryptionKey? {
        val value = packet.readUnsignedByte()
        val mark = packet.readShort()
        return ArchiveEncryptionKey(value, mark)
    }

}