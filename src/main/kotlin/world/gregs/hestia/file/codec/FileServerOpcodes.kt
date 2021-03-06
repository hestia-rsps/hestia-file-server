package world.gregs.hestia.file.codec

object FileServerOpcodes {
    const val FILE_REQUEST = 0
    const val PRIORITY_FILE_REQUEST = 1
    const val STATUS_LOGGED_IN = 2
    const val STATUS_LOGGED_OUT = 3
    const val ENCRYPTION = 4
    const val CONNECTED = 6
    const val DISCONNECT = 7
    const val HANDSHAKE = 15
}