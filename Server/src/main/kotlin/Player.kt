import study.project.battleships.models.Board
import study.project.battleships.models.Position
import study.project.battleships.models.Slot
import study.project.battleships.models.SlotState
import utils.CommunicationUtils
import java.io.*
import java.net.Socket


class Player(private val socket: Socket, players: ArrayList<Player>) : Thread() {
    private var nombre: String? = null
    private var FlujoDeEntrada: InputStream? = null
    private var DatosEntrada: DataInputStream? = null
    private var FlujoDeSalida: OutputStream? = null
    private lateinit var DatosSalida: PrintWriter
    private lateinit var board : Board
    private val players=players

    //private lateinit var ObjetosEntrada : ObjectInputStream? = null
    //private lateinit var ObjetosSalida : ObjectOutputStream? = null




    override fun run() {
        var comando: Int
        var fin = false

        try {
            FlujoDeEntrada = socket.getInputStream()
            FlujoDeSalida = socket.getOutputStream();
            DatosEntrada = DataInputStream(FlujoDeEntrada)
            DatosSalida = PrintWriter(FlujoDeSalida)
            nombre = readMsg()
            println("El servidor recibe la conexión de $nombre")

            //Se puede gestionar el final este fin, estilo, que el usuario cuando gane mande algo para cambiar este estado, o para reiniciar partida
            //Mientras tanto, el servidor nunca se apagará
            while (!fin) {
                comando=readFlag()
                when(comando){

                    //Duda para kino8, cuando un usuario mete una flag, se ejecuta el comando, okey.
                    //Pero ese comando, tiene que mandar una flag al otro usuario, manda la inversa, o manda la misma?
                    //Ahora mismo esta hecho que mande la misma, pero estoy pensando que tendria que ser la inversa no?
                    CommunicationUtils.SEND_NAME -> {
                        sendFlag(CommunicationUtils.SEND_NAME)
                        sendMsg(nombre)
                    }
                    CommunicationUtils.RECEIVE_NAME -> {
                        sendFlag(CommunicationUtils.RECEIVE_NAME)
                        System.out.println("El nombre rival es "+readMsg())
                    }

                    CommunicationUtils.SEND_POSITION ->{ //Mandas la posicion a la que disparas?
                        sendFlag(CommunicationUtils.SEND_POSITION)
                        sendPosition()
                    }
                    CommunicationUtils.RECEIVE_POSITION -> {
                        sendFlag(CommunicationUtils.RECEIVE_POSITION)
                        receivePosition()
                    }
                    CommunicationUtils.SEND_STATE-> {
                        sendFlag(CommunicationUtils.SEND_STATE)
                        sendState()
                    }

                    CommunicationUtils.RECEIVE_STATE -> {
                        sendFlag(CommunicationUtils.RECEIVE_STATE)
                        receiveState()
                    }


                    CommunicationUtils.SEND_TURN -> {
                        sendFlag(CommunicationUtils.SEND_TURN)
                        sendTurn()

                        //Echale un ojo a la logica aqui, porque al 90% seguro que con solo la flag, de send position, ya es mas que suficiente para saber que el otro empieza no?
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
    fun sendPosition() {
        //Como la comunicacion se hace con los input read, y estos van mejor con String, para simplificar codigo y no mezclar lectores de texto, pasamos las pos como "String"
        //Aunque, a la hora de gestionarlo se pasa a int y apañao.
        //Realmente, es sencillo Joaquin, lo primero que lee es la flag, y de ahi...
        val x = readMsg()
        val y = readMsg()
        val position = Position(x.toInt(),y.toInt())
        sendMsg(position.x.toString())
        sendMsg(position.y.toString())
    }

    //De esta forma, la función devuelve un Position, recuerdo que se manda como 2 string separados
    //Si no funciona correctamente, el video de pasar objetos me pilla lejos xd
    //Haz que en vez de devolver un Position, que reciba dos mensajes separados.
    private fun receivePosition():Position {
        val pos: Position
        //Como hemos dicho, la posicion se manda como 2 mensajes separados:
        val x = readMsg()
        val y = readMsg()
        pos = Position(x.toInt(),y.toInt())
        return pos
    }


    //En los state pasará lo mismo, se manda todo como string.
    //POR QUE ADEMAS QUE TIPO DE CLASE ES SLOTSTATE XD
    //Me rayaste pila, no es un string, no es un int, no entiendo ._.
    private fun sendState() {
        var slotState: SlotState
        var state = slotState.toString()
        sendMsg(state) //Suponiendo que state devuelve hit, miss... y se usa asi este tipo de clase
    }
    private fun receiveState(): SlotState {
        var slotState: SlotState
        var state = readMsg()
        slotState = SlotState.state //Suponiendo que state devuelve hit, miss... y se usa asi este tipo de clase

        return slotState
    }

    private fun sendTurn(){

    }
}