package study.project.battleships.controllers

import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.geometry.Orientation
import javafx.scene.layout.GridPane
import study.project.battleships.models.Position
import study.project.battleships.models.SlotState
import study.project.battleships.utils.ClientProvider
import java.net.URL
import java.util.*

class InitializingController : Initializable {
    @FXML
    lateinit var gp_board: GridPane
    val client = ClientProvider.get()
    val board = client.user.board

    override fun initialize(location: URL?, resources: ResourceBundle?) {
        placeShips()
        getConnection()
    }

    private fun getConnection() {
        client.connect()
    }

    fun placeShips() {
        // Place the ships randomly on the grid
        for (ship in board.ships) {
            while (!ship.isPlaced) {
                val x = (0 until board.size).random()
                val y = (0 until board.size).random()
                val orientation = Orientation.values().random()

                ship.isPlaced = if (orientation == Orientation.HORIZONTAL) {
                    if (x + ship.size > board.size) {
                        false
                    } else {
                        (x until x + ship.size).forEach {
                            if (board.grid[y][it] != SlotState.EMPTY) {
                                return@forEach
                            }
                        }
                        (x until x + ship.size).forEach {
                            board.grid[y][it] = SlotState.SHIP
                        }
                        true
                    }
                } else {
                    if (y + ship.size > board.size) {
                        false
                    } else {
                        (y until y + ship.size).forEach {
                            if (board.grid[it][x] != SlotState.EMPTY) {
                                return@forEach
                            }
                        }
                        (y until y + ship.size).forEach {
                            board.grid[it][x] = SlotState.SHIP
                        }
                        true
                    }
                }
                ship.orientation = orientation
                ship.position = Position(x, y)
            }
        }
    }
}