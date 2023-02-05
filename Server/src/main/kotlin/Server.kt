import utils.CommunicationUtils
import java.net.ServerSocket


fun main() {
    val socketServidor = ServerSocket(CommunicationUtils.DEFAULT_SERVER_PORT)
    println("Servidor disponible")
    var end = false
    var lastPlayer : Player? = null

    while (!end) {
        val socketCliente = socketServidor.accept()
        val player = Player(socketCliente)
        if (lastPlayer != null) {
            lastPlayer.opponent = player
            player.opponent = lastPlayer
        } else {
            lastPlayer = player
            end = true
        }
        player.start()
    }
}