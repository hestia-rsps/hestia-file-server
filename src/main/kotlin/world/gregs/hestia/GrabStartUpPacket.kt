package world.gregs.hestia

import world.gregs.hestia.network.packets.Packet

class GrabStartUpPacket : Packet.Builder() {

    init {
        writeByte(0)
        for (key in GRAB_SERVER_KEYS) {
            writeInt(key)
        }
    }

    companion object {
        val GRAB_SERVER_KEYS = intArrayOf(1362, 77448, 44880, 39771, 24563, 363672, 44375, 0, 1614, 0, 5340, 142976, 741080, 188204, 358294, 416732, 828327, 19517, 22963, 16769, 1244, 11976, 10, 15, 119, 817677, 1624243)
    }
}
