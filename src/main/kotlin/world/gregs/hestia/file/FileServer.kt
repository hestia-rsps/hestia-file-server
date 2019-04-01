package world.gregs.hestia.file

import world.gregs.hestia.core.Settings
import world.gregs.hestia.core.cache.Cache
import world.gregs.hestia.core.cache.CacheStore
import world.gregs.hestia.core.network.Pipeline
import world.gregs.hestia.core.network.codec.decode.SimplePacketDecoder
import world.gregs.hestia.core.network.codec.message.SimpleMessageDecoder
import world.gregs.hestia.core.network.codec.message.SimpleMessageEncoder
import world.gregs.hestia.core.network.server.Network
import world.gregs.hestia.file.codec.FileCodec

/**
 * File Server
 * Distributes cache files on demand to connected clients
 */
class FileServer {

    fun start(cache: Cache) {
        //Encoder & decoders
        val codec = FileCodec()
        //Channel pipeline
        val pipeline = Pipeline {
            //Decodes packets
            it.addLast(SimplePacketDecoder(codec))
        }

        pipeline.apply {
            //Decodes packets into messages
            add(SimpleMessageDecoder(codec))
            //Processes message handlers
            add(Handshake(Messages(cache)))
            //Encodes messages into data
            add(SimpleMessageEncoder(codec))
        }

        Network(name = "File Server", channel = pipeline).start(Settings.getInt("port", 443))
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            Settings.load()
            val cache = CacheStore()
            FileServer().start(cache)
        }
    }
}