package world.gregs.hestia

import world.gregs.hestia.network.NetworkConstants
import world.gregs.hestia.network.codec.Encoder
import world.gregs.hestia.network.codec.Pipeline
import world.gregs.hestia.network.server.Network
import world.gregs.hestia.services.Cache

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