import study.project.battleships.models.Position
import study.project.battleships.models.SlotState
import utils.CommunicationUtils
import java.io.*
import java.net.Socket


class Player(private val socket: Socket) : Thread() {
    private var nombre: String? = null
    private var FlujoDeEntrada: InputStream? = null
    private var DatosEntrada: DataInputStream? = null
    private var FlujoDeSalida: OutputStream? = null
    private lateinit var DatosSalida: PrintWriter
    var opponent: Player? = null
    private var turn = false //Si el jugador se conecta segundo va segundo

    override fun run() {
        if (opponent == null) {
            turn = true //Como fue el primero en connectarse pues se lleva la primera jugada
            while (opponent == null) {
                sleep(1000)
            }
        }
        var comando: Int
        var fin = false

        try {
            FlujoDeEntrada = socket.getInputStream()
            FlujoDeSalida = socket.getOutputStream()
            DatosEntrada = FlujoDeEntrada?.let { DataInputStream(it) }
            DatosSalida = FlujoDeSalida?.let { PrintWriter(it) }!!
            nombre = readMsg()
            println("El servidor recibe la conexión de $nombre")
            //Enviamos el nombre al enemigo
            opponent!!.sendMsg(nombre)

            //Se puede gestionar el final este fin, estilo, que el usuario cuando gane mande algo para cambiar este estado, o para reiniciar partida
            //Mientras tanto, el servidor nunca se apagará
            sendTurn()
            while (!fin) {
                comando = readFlag()
                when (comando) {

                    //Duda para kino8, cuando un usuario mete una flag, se ejecuta el comando, okey.
                    //Pero ese comando, tiene que mandar una flag al otro usuario, manda la inversa, o manda la misma?
                    //Ahora mismo esta hecho que mande la misma, pero estoy pensando que tendria que ser la inversa no?
                    //Efectivamente es la inversa y el nunca recibe la flag que manda, la mitad de las flags son de envio y las otras de recepción
                    CommunicationUtils.RECEIVE_NAME -> {
                        sendFlag(CommunicationUtils.SEND_NAME)
                        opponent!!.sendMsg(nombre)
                    }

                    CommunicationUtils.RECEIVE_POSITION -> { //Mandas la posicion a la que disparas?
                        val pos = receivePosition()
                        sendFlag(CommunicationUtils.SEND_POSITION)
                        opponent!!.sendPosition(pos)
                    }

                    CommunicationUtils.RECEIVE_STATE -> {
                        val state = receiveState()
                        sendFlag(CommunicationUtils.SEND_STATE)
                        opponent!!.sendState(state)
                    }

                    CommunicationUtils.END -> {
                        opponent!!.sendFlag(CommunicationUtils.END)
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
        val Mensaje = ByteArray(80) //Van a ser menos de 80 seguro, pero weno
        do {
            BytesLeidos = DatosEntrada!!.read(Mensaje)
        } while (BytesLeidos < 1)
        flag = String(Mensaje, 0, BytesLeidos).toInt()
        return flag
    }

    @Throws(IOException::class)
    fun sendPosition(pos: Position) {
        //Como la comunicacion se hace con los input read, y estos van mejor con String, para simplificar codigo y no mezclar lectores de texto, pasamos las pos como "String"
        //Aunque, a la hora de gestionarlo se pasa a int y apañao.
        //Realmente, es sencillo Joaquin, lo primero que lee es la flag, y de ahi...
        sendMsg(pos.x.toString())
        sendMsg(pos.y.toString())
    }

    //De esta forma, la función devuelve un Position, recuerdo que se manda como 2 string separados
    //Si no funciona correctamente, el video de pasar objetos me pilla lejos xd
    //Haz que en vez de devolver un Position, que reciba dos mensajes separados.
    private fun receivePosition(): Position {
        val pos: Position
        //Como hemos dicho, la posicion se manda como 2 mensajes separados:
        val x = readMsg()
        val y = readMsg()
        pos = Position(x.toInt(), y.toInt())
        return pos
    }

    //En los state pasará lo mismo, se manda todo como string.
    //POR QUE ADEMAS QUE TIPO DE CLASE ES SLOTSTATE XD
    //Me rayaste pila, no es un string, no es un int, no entiendo ._.
    //Es un enum, que es un tipo de clase, que tiene un valor, que es un string, que es lo que se manda(escrito por copilot)
    private fun sendState(state: SlotState) {
        sendMsg(state.toString()) //Suponiendo que state devuelve hit, miss... y se usa asi este tipo de clase
    }

    private fun receiveState(): SlotState {
        return SlotState.valueOf(readMsg())
    }

    private fun sendTurn() {
        sendMsg(turn.toString())
    }
}