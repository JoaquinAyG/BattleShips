package study.project.battleships.models

object Board {
    var size: Int = 0
    var ships: List<Ship> = listOf()
    lateinit var board: ArrayList<ArrayList<Slot>>

    fun createBoard(size: Int) {
        this.size = size
        board = ArrayList(size * size)
        for (i in 0 until size) {
            for (j in 0 until size) {
                board[i][j] = Slot(i, j)
            }
        }
    }

    fun placeShip(ship: Ship, position: Position) {
        if (ship.position.isValid(size) && !ship.isPlaced) {
            if (ship.isHorizontal) {
                if (position.x + ship.size > size) {
                    return
                }
                for (i in position.x until position.x + ship.size) {
                    if (board[i][position.y].state != SlotState.EMPTY) {
                        return
                    }
                }
                for (i in position.x until position.x + ship.size) {
                    board[i][position.y].state = SlotState.SHIP
                }
            } else {
                if(ship.position.y + ship.size > size) {
                    return
                }
                for (i in position.y until position.y + ship.size) {
                    if (board[position.x][i].state != SlotState.EMPTY) {
                        return
                    }
                }
                for (i in position.y until position.y + ship.size) {
                    board[position.x][i].state = SlotState.SHIP
                }
            }
            ship.isPlaced = true
            ships += ship
        }
    }

    fun checkHit(position: Position): Boolean {
        if (position.isValid(size)) {
            if (board[position.x][position.y].state == SlotState.SHIP) {
                board[position.x][position.y].state = SlotState.HIT
                ships.forEach {
                    if (it.position.x == position.x && it.position.y == position.y) {
                        it.hitsReceived++
                        it.checkSunk()
                    }
                }
                return true
            } else {
                board[position.x][position.y].state = SlotState.MISS
            }
        }
        return false
    }

}