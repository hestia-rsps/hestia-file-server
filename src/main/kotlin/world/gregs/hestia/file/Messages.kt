package world.gregs.hestia.file

import world.gregs.hestia.core.cache.Cache
import world.gregs.hestia.core.network.codec.MessageHandshakeDispatcher
import world.gregs.hestia.file.handlers.*

class Messages(cache: Cache) : MessageHandshakeDispatcher() {
    init {
        bind(HandshakeHandler(), true)
        bind(EncryptionKeyHandler())
        bind(DisconnectHandler())
        bind(ConnectedHandler())
        bind(LoginStatusHandler())
        bind(FileRequestHandler(cache))
    }
}