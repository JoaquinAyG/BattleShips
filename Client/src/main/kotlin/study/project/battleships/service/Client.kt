package study.project.battleships.service

import study.project.battleships.models.Position
import study.project.battleships.models.SlotState
import study.project.battleships.models.User
import study.project.battleships.utils.CommunicationUtils
import java.io.*
import java.net.Socket
import java.util.*

class Client(
    val user: User
) : Thread() {

    lateinit var socket: Socket
    val id = UUID.randomUUID()
    lateinit var inputStream: InputStream
    lateinit var outputStream: OutputStream
    lateinit var dataInputStream: DataInputStream
    lateinit var dataOutputStream: PrintWriter

    override fun run() {
        if (!::socket.isInitialized){
            error("Socket is not initialized, remember to call the getConnection method before run")
        }
        sendName()
        receiveName()
        while (true) {
            val message = dataInputStream.readUTF()
            println(message)
        }
    }

    private fun sendName() {
        dataOutputStream.println(user.name)
    }

    fun getConnection(socket: Socket) {
        this.socket = socket
        inputStream = socket.getInputStream()
        outputStream = socket.getOutputStream()
        dataInputStream = DataInputStream(inputStream)
        dataOutputStream = PrintWriter(outputStream, true)
    }

    fun sendPos(pos: Position) {
        dataOutputStream.println(pos.x)
        dataOutputStream.println(pos.y)
    }

    fun sendSlotState(state : SlotState){
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
        val x = readMsg()
        val y = readMsg()
        pos = Position(x.toInt(), y.toInt())
        return pos
    }

    fun sendPosition(pos: Position) {
        sendMsg(pos.x.toString())
        sendMsg(pos.y.toString())
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
        do {
            BytesLeidos = dataInputStream.read(Mensaje)
        } while (BytesLeidos < 1)
        msg = String(Mensaje, 0, BytesLeidos)
        return msg
    }

    fun sendMsg(msg: String?) {
        dataOutputStream.print(msg)
        dataOutputStream.flush()
    }
}