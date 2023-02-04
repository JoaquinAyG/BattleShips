package study.project.battleships.models

class Position(val x: Int, val y: Int) {
    fun isValid(boardSize: Int): Boolean {
        return x in 0 until boardSize && y in 0 until boardSize
    }
}
