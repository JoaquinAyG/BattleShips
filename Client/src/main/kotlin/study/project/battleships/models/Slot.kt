package study.project.battleships.models

class Slot(
    val x: Int,
    val y: Int,
    var state : SlotState = SlotState.EMPTY
) {

}
