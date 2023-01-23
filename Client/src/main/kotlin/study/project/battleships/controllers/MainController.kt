package study.project.battleships.controllers

import study.project.battleships.extensions.show
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.TextField
import javafx.scene.image.ImageView
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