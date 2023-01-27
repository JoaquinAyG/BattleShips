package study.project.battleships.models

import study.project.battleships.BOARD_SIZE

class EnemyBoard {
    val board: Array<Array<SlotState>> = Array(BOARD_SIZE) { Array(BOARD_SIZE) { SlotState.EMPTY } }
    fun getSlotState(x: Int, y: Int): SlotState {
        return board[x][y]
    }
    fun setSlotState(x: Int, y: Int, slotState: SlotState) {
        board[x][y] = slotState
    }
}