package study.project.battleships.models

class Board {
    var size: Int = 0
    var ships: List<Ship> = listOf()
    lateinit var grid: ArrayList<ArrayList<SlotState>>

    fun createBoard(size: Int) {
        this.size = size
        grid = ArrayList<ArrayList<SlotState>>(size).apply {
            for (i in 0 until size) {
                add(ArrayList<SlotState>(size).apply { repeat(size) { add(SlotState.EMPTY) } })
            }
        }
    }

    init {
        size = 10
        createBoard(10)
        ships = listOf(
            Ship(5),
            Ship(4),
            Ship(3),
            Ship(3),
            Ship(2)
        )
    }

    fun checkHit(position: Position): Boolean {
        if (position.isValid(size)) {
            if (grid[position.x][position.y] == SlotState.SHIP) {
                grid[position.x][position.y] = SlotState.HIT
                ships.forEach {
                    if (it.position.x == position.x && it.position.y == position.y) {
                        it.hitsReceived++
                        it.checkSunk()
                    }
                }
                return true
            } else {
                grid[position.x][position.y] = SlotState.MISS
            }
        }
        return false
    }

}