package study.project.battleships.models

class Ship(
    var size: Int,
    var position: Position,
    var isPlaced: Boolean,
    var isHorizontal: Boolean,
    var isSunk: Boolean = false,
    var hitsReceived: Int = 0
) {

    fun checkSunk(): Boolean {
        isSunk = hitsReceived == size
        return isSunk
    }
}
