package study.project.battleships.models

import study.project.battleships.BOARD_SIZE

class EnemyBoard {
    val grid: Array<Array<SlotState>> = Array(BOARD_SIZE) { Array(BOARD_SIZE) { SlotState.EMPTY } }
    fun getSlotState(pos: Position): SlotState {
        return grid[pos.x][pos.y]
    }
    fun setSlotState(pos: Position, slotState: SlotState) {
        grid[pos.x][pos.y] = slotState
    }
}