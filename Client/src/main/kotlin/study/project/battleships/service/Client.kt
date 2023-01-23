package study.project.battleships.service

import java.io.InputStream
import java.io.OutputStream
import java.net.Socket
import java.util.*

class Client {
    val id = UUID.randomUUID()
    var name = "User"
    lateinit var socket: Socket
    lateinit var inputStream: InputStream
    lateinit var outputStream: OutputStream
}