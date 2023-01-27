package study.project.battleships.models

import java.io.Serializable

class Slot(
    val x: Int,
    val y: Int,
    var state : SlotState = SlotState.EMPTY
) : Serializable {

}
