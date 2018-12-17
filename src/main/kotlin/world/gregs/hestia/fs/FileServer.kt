package world.gregs.hestia.fs

import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.network.NetworkConstants
import world.gregs.hestia.core.network.codec.Encoder
import world.gregs.hestia.core.network.codec.Pipeline
import world.gregs.hestia.core.network.server.Network
import world.gregs.hestia.core.services.Cache

class FileServer {
    private val network: Network = Network(name = "File Server", channel = Pipeline(FileRequestHandshake(), Encoder()))

    fun start() {
        network.start(NetworkConstants.BASE_PORT + 1)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load()
            Cache.init()
            FileServer().start()
        }
    }
}