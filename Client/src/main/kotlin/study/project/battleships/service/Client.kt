package study.project.battleships.service

import java.io.*
import java.net.Socket
import java.util.*

class Client(
    val userName: String = "User",
    socket: Socket
) : Thread() {

    val id = UUID.randomUUID()
    val sc = Scanner(System.`in`)
    val inputStream: InputStream = socket.getInputStream()
    val outputStream: OutputStream = socket.getOutputStream()
    val dataInputStream: DataInputStream = DataInputStream(inputStream)
    val dataOutputStream: PrintWriter = PrintWriter(outputStream, true)
    val objecOutputStream: OutputStream = ObjectOutputStream(outputStream)

    override fun run() {
        sendName()
        while (true) {
            val message = dataInputStream.readUTF()
            println(message)
        }
    }

    private fun sendName() {
        dataOutputStream.println(userName)
    }

}