package study.project.battleships.utils

import study.project.battleships.models.Position
import study.project.battleships.models.SlotState
import study.project.battleships.service.Client
import java.net.Socket

object ClientController {
    private var credentials: Pair<String, Socket?> = Pair("", null)
    lateinit var client: Client
    fun get(): Client {
        return client
    }

    fun set(client: Client) {
        this.client = client
    }

    fun setCredentials(credentials: Pair<String, Socket?>) {
        this.credentials = credentials
    }
    fun getCredentials(): Pair<String, Socket?> {
        return credentials
    }
    fun sendPosition(pos : Position){
        client.sendPosition(pos)
    }
    fun sendSlotState(state : SlotState){
        client.sendSlotState(state)
    }
    fun getPosition() : Position{
        println("esperando posicion")
        return client.getPosition()
    }
    fun getSlotState() : SlotState{
        println("esperando slotState")
        return client.getSlotState()
    }
    fun getName() : String{
        println("esperando nombre")
        return client.receiveName()
    }
    fun readFlagIsEnd() : Boolean{
        println("esperando flag")
        return client.readFlagIsEnd()
    }
    fun getTurn() : Boolean{
        println("esperando turno")
        return client.getTurn()
    }
    fun getClientName() : String{
        return client.user.name
    }
}