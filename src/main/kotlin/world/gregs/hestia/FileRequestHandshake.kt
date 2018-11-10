package world.gregs.hestia

import io.netty.buffer.ByteBuf
import org.slf4j.LoggerFactory
import world.gregs.hestia.network.NetworkConstants
import world.gregs.hestia.network.Session
import world.gregs.hestia.network.codec.inbound.HandshakeHandler
import world.gregs.hestia.network.packets.out.Response
import world.gregs.hestia.services.Cache

class FileRequestHandshake : HandshakeHandler() {

    private var encryptionValue = 0
    private val logger = LoggerFactory.getLogger(FileRequestHandshake::class.java)

    override fun handshake(session: Session, buffer: ByteBuf): Boolean {
        if(validate(session, buffer, 15, 8)) {
            if (buffer.readInt() != NetworkConstants.CLIENT_MAJOR_VERSION || buffer.readInt() != NetworkConstants.CLIENT_MINOR_VERSION) {
                session.respond(Response.GAME_UPDATED)
                logger.info("Invalid game version ${session.getHost()}")
                return false
            }

            session.write(GrabStartUpPacket())
            return true
        }
        return false
    }

    override fun process(session: Session, buffer: ByteBuf) {
        while (buffer.readableBytes() > 0 && session.channel?.isOpen == true) {
            val packetId = buffer.readUnsignedByte().toInt()
            if (packetId == 0 || packetId == 1) {
                decodeRequestCacheContainer(session, buffer, packetId == 1)
            } else {
                decodeOtherPacket(session, buffer, packetId)
            }
        }
    }

    private fun decodeRequestCacheContainer(session: Session, buffer: ByteBuf, priority: Boolean) {
        val indexId = if(buffer.isReadable(1)) buffer.readUnsignedByte().toInt() else 0
        val archiveId = if(buffer.isReadable(4)) buffer.readInt() else 0
        if (archiveId < 0) {
            return
        }
        if (indexId != 255) {
            if (Cache.indexCount() <= indexId || Cache.getIndex(indexId)?.archiveExists(archiveId) == false) {
                return
            }
        } else if (archiveId != 255) {
            if (Cache.indexCount() <= archiveId || Cache.getIndex(archiveId) == null) {
                return
            }
        }

        val encryption = if(indexId == 255 && archiveId == 255) 0 else encryptionValue
        session.write(CacheArchiveData(indexId, archiveId, priority, encryption))
    }

    private fun decodeOtherPacket(session: Session, buffer: ByteBuf, packetId: Int) {
        if (packetId == 7) {
            logger.info("Packet id 7")
            session.close()
            return
        }
        if (packetId == 4) {
            encryptionValue = if(buffer.isReadable(1)) buffer.readUnsignedByte().toInt() else 0
            val a = if(buffer.isReadable(2)) buffer.readShort().toInt() else 0
            if (a != 0) {
                logger.info("Invalid decryption packet $a")
                session.close()
            }
        } else {
            if(buffer.isReadable(3)) {
                buffer.skipBytes(3)
            } else {
                buffer.skipBytes(buffer.readableBytes())
            }
        }
    }
}