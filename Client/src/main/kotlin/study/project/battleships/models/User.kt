package study.project.battleships.models

class User {
    var name: String = ""
    val ships: MutableList<Ship> = mutableListOf()
}