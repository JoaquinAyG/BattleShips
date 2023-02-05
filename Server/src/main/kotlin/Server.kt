import utils.CommunicationUtils
import java.net.ServerSocket


fun main() {
    val socketServidor = ServerSocket(CommunicationUtils.DEFAULT_SERVER_PORT)
    println("Servidor disponible")
    var end = false
    var lastPlayer : Player? = null

    while (!end) {
        val socketCliente = socketServidor.accept()
        println("Cliente conectado")
        val player = Player(socketCliente)
        if (lastPlayer != null) {
            println("Cliente con oponente")
            lastPlayer.opponent = player
            player.opponent = lastPlayer
        } else {
            println("Cliente sin oponente")
            lastPlayer = player
        }
        player.start()
        println("player.start()")
    }
}