package study.project.battleships.controllers

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.geometry.Orientation
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import javafx.stage.Stage
import study.project.battleships.BattleShipsClient
import study.project.battleships.models.Board
import study.project.battleships.models.Position
import study.project.battleships.models.SlotState
import study.project.battleships.models.User
import study.project.battleships.service.Client
import study.project.battleships.utils.ClientController
import java.net.URL
import java.util.*

class InitializingController : Initializable {
    @FXML
    lateinit var gp_board: GridPane
    val credentials = ClientController.getCredentials()
    lateinit var client : Client
    lateinit var board : Board

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        client = Client(User(credentials.first))
        ClientController.set(client)
        board = client.user.board
        placeShips()
        printBoard()
        getConnection()
        //startGame()
    }

    private fun printBoard() {
        for (i in 0..9) {
            for (j in 0..9) {
                val waterStream = BattleShipsClient::class.java.getResourceAsStream("images/water.jpg")
                val shipStream = BattleShipsClient::class.java.getResourceAsStream("images/ship.jpg")
                val iv =
                    if (board.grid[i][j] == SlotState.SHIP)
                        ImageView(Image(shipStream))
                    else
                        ImageView(Image(waterStream))
                iv.fitWidth = 40.0
                iv.fitHeight = 40.0
                gp_board.add(iv, i, j)
            }
        }
    }

    private fun startGame() {
        val stage = gp_board.scene.window as Stage
        val root = FXMLLoader.load<Parent>(BattleShipsClient::class.java.getResource("game_view.fxml"))
        val newScene = Scene(root, 600.0, 450.0)
        println("Scene loaded")
        stage.scene = newScene
        stage.show()
    }

    private fun getConnection() {
        credentials.second?.let { client.getConnection(it) }
    }

    fun placeShips() {
        // Place the ships randomly on the grid
        for (ship in board.ships) {
            while (!ship.isPlaced) {
                var can = false
                val x = (0 until board.size).random()
                val y = (0 until board.size).random()
                val orientation = Orientation.values().random()

                ship.isPlaced = if (orientation == Orientation.HORIZONTAL) {
                    if (x + ship.size > board.size) {
                        false
                    } else {
                        (x until x + ship.size).forEach {
                            can = false
                            if (board.grid[y][it] != SlotState.EMPTY) {
                                return@forEach
                            }
                            can = true
                        }
                        if (can) {
                            (x until x + ship.size).forEach {
                                board.grid[y][it] = SlotState.SHIP
                            }
                            true
                        } else
                            false

                    }
                } else {
                    if (y + ship.size > board.size) {
                        false
                    } else {
                        (y until y + ship.size).forEach {
                            can = false
                            if (board.grid[it][x] != SlotState.EMPTY) {
                                return@forEach
                            }
                            can = true
                        }
                        (y until y + ship.size).forEach {
                            board.grid[it][x] = SlotState.SHIP
                        }
                        if (can) {
                            (y until y + ship.size).forEach {
                                board.grid[it][x] = SlotState.SHIP
                            }
                            true
                        } else
                            false
                    }
                }
                ship.orientation = orientation
                ship.position = Position(x, y)
            }
        }
    }
}