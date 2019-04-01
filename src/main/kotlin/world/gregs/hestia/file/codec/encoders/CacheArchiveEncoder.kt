package world.gregs.hestia.file.codec.encoders

import world.gregs.hestia.core.network.codec.message.MessageEncoder
import world.gregs.hestia.core.network.codec.packet.PacketBuilder
import world.gregs.hestia.file.codec.encoders.messages.CacheArchiveFile

class CacheArchiveEncoder : MessageEncoder<CacheArchiveFile>() {
    override fun encode(builder: PacketBuilder, message: CacheArchiveFile) {
        val (index, archive, settings, length, data, size, encryption) = message
        builder.apply {
            val start = buffer.writerIndex()
            //Write header
            writeByte(index)
            writeShort(archive)
            writeByte(settings)
            writeInt(length)

            //Write data split into chunks of 512
            encode(this, data, size, 5, 512)

            //Write encryption value if present
            if (encryption != 0) {
                for (i in start until buffer.arrayOffset()) {
                    buffer.setByte(i, buffer.getByte(i).toInt() xor encryption)
                }
            }
        }
    }

    companion object {
        fun encode(builder: PacketBuilder, data: ByteArray, length: Int, dataHeader: Int, chunkSize: Int) {
            //Calculate the last index after writing
            val endIndex = length + dataHeader
            //Calculate the number of chunks to split the data into
            val chunks = Math.ceil((builder.position() + length) / (chunkSize - 1.0)).toInt()
            //Offset by the data header
            var offset = dataHeader
            val startOffset = Math.floor(builder.position() / chunkSize.toDouble()).toInt()
            //For every chunk (inclusive)
            for (i in startOffset..startOffset + chunks) {
                //Break if at the end of the data (empty chunk)
                if (offset == endIndex) {
                    break
                }
                //If at a chunk; write a delimiter
                if (builder.position() % chunkSize == 0) {
                    builder.writeByte(-1)
                }
                //The start of the chunk
                val start = builder.position()
                //The next chunk index
                val end = (i + 1) * chunkSize
                //Amount of space available to write to in this chunk
                val availableSize = end - start
                //Amount of data to write, capped at max available
                val size = Math.min(availableSize, endIndex - offset)
                //Write bytes
                builder.writeBytes(data, offset, size)
                //Increase offset by amount written
                offset += size
            }
        }
    }
}