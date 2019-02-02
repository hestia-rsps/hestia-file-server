package world.gregs.hestia.file

import io.netty.channel.ChannelHandler
import world.gregs.hestia.core.network.codec.HandshakeDispatcher
import world.gregs.hestia.core.network.codec.message.SimpleMessageHandshake

@ChannelHandler.Sharable
class Handshake(dispatcher: HandshakeDispatcher) : SimpleMessageHandshake(dispatcher)