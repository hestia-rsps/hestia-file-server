package world.gregs.hestia.file.codec.encoders

import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.file.codec.encoders.messages.GrabKeyStartup

class GrabKeyEncoder : MessageEncoder<GrabKeyStartup>() {

    override fun encode(builder: PacketBuilder, message: GrabKeyStartup) {
        keyBuffer.resetReaderIndex()
        builder.writeBytes(keyBuffer)
    }

    companion object {
        private val GRAB_SERVER_KEYS = intArrayOf(1362, 77448, 44880, 39771, 24563, 363672, 44375, 0, 1614, 0, 5340, 142976, 741080, 188204, 358294, 416732, 828327, 19517, 22963, 16769, 1244, 11976, 10, 15, 119, 817677, 1624243)
        private var keyBuffer: ByteBuf = Unpooled.buffer(109)

        init {
            keyBuffer.writeByte(0)
            for (key in GRAB_SERVER_KEYS) {
                keyBuffer.writeInt(key)
            }
        }
    }

}