package study.project.battleships.controllers

import study.project.battleships.extensions.show
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
import javafx.stage.Stage
import study.project.battleships.APP_NAME
import study.project.battleships.BattleShipsClient
import study.project.battleships.GITHUB_LINK
import java.awt.Desktop
import java.io.IOException
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
            val node = it.source as Button
            val stage = node.scene.window as Stage
            val root = FXMLLoader.load<Parent>(BattleShipsClient::class.java.getResource("game_view.fxml"))
            val newScene = Scene(root, 600.0, 450.0)
            println("Scene loaded")
            stage.scene = newScene
            stage.show()
        }
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