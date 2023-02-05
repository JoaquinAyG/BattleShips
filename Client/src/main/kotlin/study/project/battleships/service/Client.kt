package study.project.battleships.service

import study.project.battleships.models.Position
import study.project.battleships.models.SlotState
import study.project.battleships.models.User
import study.project.battleships.utils.CommunicationUtils
import java.io.*
import java.net.Socket
import java.util.*

class Client(
    val user: User,
    val socket: Socket
) {
    val id = UUID.randomUUID()
    lateinit var inputStream: InputStream
    lateinit var outputStream: OutputStream
    lateinit var dataInputStream: DataInputStream
    lateinit var dataOutputStream: PrintWriter
    var enemyName = ""

    init {
        getConnection()
        sendName()
    }

    private fun sendName() {
        sendMsg(user.name)
    }

    fun getConnection() {
        inputStream = socket.getInputStream()
        outputStream = socket.getOutputStream()
        dataInputStream = DataInputStream(inputStream)
        dataOutputStream = PrintWriter(outputStream, false)
    }

    fun sendSlotState(state : SlotState){
        sendFlag(CommunicationUtils.SEND_SLOT_TYPE)
        sendMsg(state.toString())
    }

    fun receiveName(): String {
        return readMsg()
    }

    fun getSlotState(): SlotState {
        return SlotState.valueOf(readMsg())
    }

    fun getPosition(): Position {
        val pos: Position
        val xy = readMsg().split(" ")
        pos = Position(xy[0].toInt(), xy[0].toInt())
        return pos
    }

    fun sendPosition(pos: Position) {
        sendFlag(CommunicationUtils.SEND_POSITION)
        sendMsg(pos.x.toString() + " " + pos.y.toString())
    }

    fun readFlagIsEnd(): Boolean {
        var flag = dataInputStream.readUTF()
        while (flag.isEmpty()) {
            flag = dataInputStream.readUTF()
        }
        return flag.toInt() == CommunicationUtils.END
    }

    fun readMsg(): String {
        val msg: String
        var BytesLeidos: Int
        val Mensaje = ByteArray(80)
        println("esperando msg")
        do {
            BytesLeidos = dataInputStream!!.read(Mensaje)
        } while (BytesLeidos < 1)
        msg = String(Mensaje, 0, BytesLeidos)
        return msg
    }

    fun sendMsg(msg: String?) {
        dataOutputStream.print(msg)
        dataOutputStream.flush()
    }

    fun getTurn(): Boolean {
        val msg = readMsg().toBoolean()
        println(msg.toString())
        return msg
    }

    private fun sendFlag(flag: Int?) {
        dataOutputStream.print(flag)
        dataOutputStream.flush()
    }
}