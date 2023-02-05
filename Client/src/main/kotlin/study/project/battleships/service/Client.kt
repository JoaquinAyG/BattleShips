package study.project.battleships.service

import study.project.battleships.models.User
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
}