import study.project.battleships.models.Position
import study.project.battleships.models.SlotState
import utils.CommunicationUtils
import java.io.*
import java.net.Socket


class Player(private val socket: Socket) : Thread() {
    lateinit var nombre: String
    lateinit var FlujoDeEntrada: InputStream
    lateinit var DatosEntrada: DataInputStream
    lateinit var FlujoDeSalida: OutputStream
    private lateinit var DatosSalida: PrintWriter
    var opponent: Player? = null
    private var turn = false

    override fun run() {
        if (opponent == null) {
            turn = true
            while (opponent == null) {
                sleep(1000)
            }
        }
        var comando: Int
        var fin = false

        try {
            FlujoDeEntrada = socket.getInputStream()
            FlujoDeSalida = socket.getOutputStream()
            DatosEntrada = DataInputStream(FlujoDeEntrada)
            DatosSalida = PrintWriter(FlujoDeSalida, false)
            nombre = readMsg()
            println("El servidor recibe la conexión de $nombre")
            sendTurn()
            println("El servidor manda turno a $nombre")

            while (!fin) {
                comando = readFlag()
                println("El servidor lee comando de $nombre, comando: $comando")
                when (comando) {
                    CommunicationUtils.RECEIVE_NAME -> {
                        opponent!!.sendMsg(nombre)
                        println("El servidor manda nombre a ${opponent!!.nombre}")

                    }

                    CommunicationUtils.RECEIVE_POSITION -> {
                        val pos = receivePosition()
                        println("El servidor recive posicion de ${nombre}")
                        opponent!!.sendPosition(pos)
                        println("El servidor manda pos a ${opponent!!.nombre}")

                    }

                    CommunicationUtils.RECEIVE_STATE -> {
                        val state = receiveState()
                        opponent!!.sendState(state)
                    }

                    CommunicationUtils.END -> {
                        fin = true
                    }
                }
            }
            socket.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun sendMsg(msg: String?) {
        DatosSalida.print(msg)
        DatosSalida.flush()
    }

    @Throws(IOException::class)
    fun readMsg(): String {
        val msg: String
        var BytesLeidos: Int
        val Mensaje = ByteArray(80)
        do {
            BytesLeidos = DatosEntrada!!.read(Mensaje)
        } while (BytesLeidos < 1)
        msg = String(Mensaje, 0, BytesLeidos)
        return msg
    }

    fun sendFlag(flag: Int?) {
        DatosSalida.print(flag)
        DatosSalida.flush()
    }

    @Throws(IOException::class)
    fun readFlag(): Int {
        val flag: Int
        var BytesLeidos: Int
        val Mensaje = ByteArray(80)
        do {
            BytesLeidos = DatosEntrada!!.read(Mensaje)
        } while (BytesLeidos < 1)
        flag = String(Mensaje, 0, BytesLeidos).toInt()
        return flag
    }

    @Throws(IOException::class)
    fun sendPosition(pos: Position) {
        sendMsg(pos.x.toString() + " " + pos.y.toString())
    }
    private fun receivePosition(): Position {
        val pos: Position
        //Como hemos dicho, la posicion se manda como 2 mensajes separados:
        val xy = readMsg().split(" ")
        pos = Position(xy[0].toInt(), xy[0].toInt())
        return pos
    }
    private fun sendState(state: SlotState) {
        sendMsg(state.toString())
    }
    private fun receiveState(): SlotState {
        return SlotState.valueOf(readMsg())
    }
    private fun sendTurn() {
        println(turn.toString())
        sendMsg(turn.toString())
    }
}