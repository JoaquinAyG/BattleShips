package study.project.battleships.controllers

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import javafx.scene.text.Text
import study.project.battleships.BattleShipsClient
import study.project.battleships.models.Position
import study.project.battleships.service.Client
import study.project.battleships.utils.ClientController
import java.net.URL
import java.util.*

class GameController: Initializable {

    @FXML
    lateinit var tv_enemy_name: Text
    @FXML
    lateinit var tv_name: Text
    @FXML
    lateinit var gp_board: GridPane
    private lateinit var client: Client
    val enemyName = ""
    val enemyBoard = Array(10) { Array(10) { 0 } }
    val turn = false

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        client = ClientController.client
        tv_name.text = client.user.name
        for (i in 0..9) {
            for (j in 0..9) {
                val stream = BattleShipsClient::class.java.getResourceAsStream("images/water.jpg")
                val iv = ImageView(Image(stream))
                iv.fitWidth = 40.0
                iv.fitHeight = 40.0
                gp_board.add(iv, i, j)
            }
        }
        turn = client.getTurn()
        gp_board.addEventHandler( javafx.scene.input.MouseEvent.MOUSE_CLICKED ) { event ->
            if (turn) {
                val x = event.x.toInt() / 40
                val y = event.y.toInt() / 40
                val pos = Position(x, y)
                client.sendPosition(pos)
                val state = client.getSlotState()
                val stream = BattleShipsClient::class.java.getResourceAsStream("images/ship.jpg")
                val iv = ImageView(Image(stream))
                iv.fitWidth = 40.0
                iv.fitHeight = 40.0
                gp_board.add(iv, x, y)
            }
        }
    }
}