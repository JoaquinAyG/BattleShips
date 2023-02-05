package study.project.battleships.models

import javafx.geometry.Orientation

class Ship(
    var size: Int,
) {

    var position: Position = Position(-1, -1)
    var isPlaced: Boolean = false
    var orientation: Orientation = Orientation.VERTICAL
    var isSunk: Boolean = false
    var hitsReceived: Int = 0
    fun checkSunk(): Boolean {
        isSunk = hitsReceived == size
        return isSunk
    }
}
