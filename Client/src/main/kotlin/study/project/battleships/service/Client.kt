package study.project.battleships.service

import java.io.DataInputStream
import java.io.InputStream
import java.io.ObjectOutputStream
import java.io.OutputStream
import java.io.PrintWriter
import java.net.Socket
import java.util.*

class Client(
    socket: Socket
) : Thread() {

    val id = UUID.randomUUID()
    var name = "User"
    val sc = Scanner(System.`in`)
    val inputStream: InputStream = socket.getInputStream()
    val outputStream: OutputStream = socket.getOutputStream()
    val dataInputStream: DataInputStream = DataInputStream(inputStream)
    val dataOutputStream: PrintWriter = PrintWriter(outputStream, true)
    val objecOutputStream: OutputStream = ObjectOutputStream(outputStream)
    
    
}