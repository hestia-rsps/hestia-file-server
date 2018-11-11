package world.gregs.hestia.fs

import world.gregs.hestia.core.network.packets.Packet
import world.gregs.hestia.core.services.Cache


class CacheArchiveData(index: Int, archive: Int, priority: Boolean, encryption: Int) : Packet.Builder() {
    init {
        val data = Cache.getFile(index, archive)
        val compression = data[0].toInt() and 0xff
        val length = ((data[1].toInt() and 0xff shl 24) + (data[2].toInt() and 0xff shl 16) + (data[3].toInt() and 0xff shl 8) + (data[4].toInt() and 0xff))
        var settings = compression
        if (!priority) {
            settings = settings or 0x80
        }

        writeByte(index)
        writeInt(archive)
        writeByte(settings)
        writeInt(length)

        val realLength = if (compression != 0) length + 4 else length
        for (i in 5 until realLength + 5) {
            if (position() % 512 == 0) {
                writeByte(255)
            }
            writeByte(data[i].toInt())
        }

        if (encryption != 0) {
            for (i in 0 until buffer.arrayOffset()) {
                buffer.setByte(i, buffer.getByte(i).toInt() xor encryption)
            }
        }
    }
}