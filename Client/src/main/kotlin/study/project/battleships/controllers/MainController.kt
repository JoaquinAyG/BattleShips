package study.project.battleships.controllers

import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.stage.Stage
import study.project.battleships.BattleShipsClient
import study.project.battleships.GITHUB_LINK
import study.project.battleships.extensions.show
import study.project.battleships.utils.ClientController
import study.project.battleships.utils.CommunicationUtils
import java.awt.Desktop
import java.io.IOException
import java.net.Socket
import java.net.URI
import java.net.URISyntaxException
import java.net.URL
import java.util.*


class MainController: Initializable {

    private val desktop = Desktop.getDesktop()
    @FXML
    lateinit var btn_play: Button
    @FXML
    lateinit var et_name: TextField
    @FXML
    lateinit var et_port: TextField

    override fun initialize(p0: URL?, p1: ResourceBundle?) {
        btn_play.setOnAction {
            if (validateFields()) {
                val port = createPort()
                val credentials = Pair<String, Socket?> (et_name.text.toString(), Socket(port.first, port.second))
                ClientController.setCredentials(credentials)
                val node = it.source as Button
                val stage = node.scene.window as Stage
                val root = FXMLLoader.load<Parent>(BattleShipsClient::class.java.getResource("initializing_view.fxml"))
                val newScene = Scene(root, 600.0, 450.0)
                println("Scene loaded")
                stage.scene = newScene
                stage.show()
            }
        }
    }

    private fun createPort() : Pair<String, Int> {
        val port = when {
            et_port.text.isEmpty() -> "${CommunicationUtils.DEFAULT_SERVER_HOST}:${CommunicationUtils.DEFAULT_SERVER_PORT}"
            et_port.text.split(":").size == 2 -> et_port.text
            else -> "${CommunicationUtils.DEFAULT_SERVER_HOST}:${CommunicationUtils.DEFAULT_SERVER_PORT}"
        }
        try {
            val portInt = port.split(":")[1].toInt()
            if (portInt < 0 || portInt > 65535) {
                Alert(Alert.AlertType.ERROR).show("Error", "Puerto incorrecto")
                return Pair(CommunicationUtils.DEFAULT_SERVER_HOST, CommunicationUtils.DEFAULT_SERVER_PORT)
            }
        } catch (ex: NumberFormatException) {
            Alert(Alert.AlertType.ERROR).show("Error", "Puerto incorrecto")
            return Pair(CommunicationUtils.DEFAULT_SERVER_HOST, CommunicationUtils.DEFAULT_SERVER_PORT)
        }
        return Pair(port.split(":")[0], port.split(":")[1].toInt())
    }

    private fun validateFields() : Boolean {
        return et_name.text.isNotEmpty()
    }

    fun linkGitHub() {
        try {
            desktop.browse(URI(GITHUB_LINK))
        } catch (ex: IOException) {
            Alert(Alert.AlertType.ERROR).show("Error while opening the page", "capa que no tienes wifi")
        } catch (ex: URISyntaxException) {
            Alert(Alert.AlertType.ERROR).show("Error while opening the page", "capa que no tienes wifi")
        }
    }

}