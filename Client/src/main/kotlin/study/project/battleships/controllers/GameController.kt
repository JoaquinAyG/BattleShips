package study.project.battleships.controllers

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import javafx.scene.text.Text
import study.project.battleships.BattleShipsClient
import study.project.battleships.models.EnemyBoard
import study.project.battleships.models.Position
import study.project.battleships.models.SlotState
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
    val enemyBoard = EnemyBoard()
    private var turn = false
    override fun initialize(location: URL?, resources: ResourceBundle?) {
        turn = ClientController.getTurn()
        tv_name.text = ClientController.getClientName()
        for (i in 0..9) {
            for (j in 0..9) {
                val stream = BattleShipsClient::class.java.getResourceAsStream("images/water.jpg")
                val iv = ImageView(Image(stream))
                iv.fitWidth = 40.0
                iv.fitHeight = 40.0
                gp_board.add(iv, i, j)
            }
        }
        gp_board.addEventHandler( javafx.scene.input.MouseEvent.MOUSE_CLICKED ) { event ->
            if (turn && enemyBoard.grid[event.x.toInt() / 40][event.y.toInt() / 40] == SlotState.EMPTY) {
                val x = event.x.toInt() / 40
                val y = event.y.toInt() / 40
                ClientController.sendPosition(Position(x, y))
                val state = ClientController.getSlotState()
                enemyBoard.grid[x][y] = state
                val stream = when (state) {
                    SlotState.SHIP -> BattleShipsClient::class.java.getResourceAsStream("images/ship.jpg")
                    SlotState.EMPTY -> BattleShipsClient::class.java.getResourceAsStream("images/miss.jpg")
                    SlotState.HIT -> BattleShipsClient::class.java.getResourceAsStream("images/ship.jpg")
                    SlotState.MISS -> BattleShipsClient::class.java.getResourceAsStream("images/miss.jpg")
                }
                val iv = ImageView(Image(stream))
                iv.fitWidth = 40.0
                iv.fitHeight = 40.0
                gp_board.add(iv, x, y)
                turn = false

            }
            gameLoop()
        }
    }

    private fun gameLoop() {
        while (!turn) {
            val pos = ClientController.getPosition()
            ClientController.sendSlotState(ClientController.client.user.board.grid[pos.x][pos.y])
            turn = true
        }
    }
}