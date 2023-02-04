import java.net.ServerSocket
import java.net.Socket


fun main(args: Array<String>) {
    val SocketServidor: ServerSocket
    var SocketCliente: Socket?
    var player: Player
    var players: ArrayList<Player> = ArrayList<Player>()
    var total_players = 0
    val fin = false
    SocketServidor = ServerSocket(8000)
    println("Servidor disponible")

    while (!fin) {
        SocketCliente = SocketServidor.accept()
        player = Player(SocketCliente, players)
        player.start()
        total_players++
    }
    SocketServidor.close()
}