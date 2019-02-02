package world.gregs.hestia.file.codec

import world.gregs.hestia.core.network.codec.MessageCodec
import world.gregs.hestia.core.network.protocol.encoders.ClientResponseEncoder
import world.gregs.hestia.file.codec.decoders.*
import world.gregs.hestia.file.codec.encoders.CacheArchiveEncoder
import world.gregs.hestia.file.codec.encoders.GrabKeyEncoder

class FileCodec : MessageCodec() {
    init {
        bind(EncryptionKeyDecoder())
        bind(FileRequestDecoder())
        bind(FileServerConnectedDecoder())
        bind(FileHandshakeDecoder())
        bind(FileServerDisconnectDecoder())
        bind(FileServerLoginStatusDecoder())

        bind(CacheArchiveEncoder())
        bind(ClientResponseEncoder())
        bind(GrabKeyEncoder())
    }
}