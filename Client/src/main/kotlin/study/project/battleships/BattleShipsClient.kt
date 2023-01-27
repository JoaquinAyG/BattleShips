package study.project.battleships

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.paint.Color
import javafx.stage.Stage

class BattleShipsClient : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(BattleShipsClient::class.java.getResource("main_view.fxml"))
        val scene = Scene(fxmlLoader.load(), 600.0, 400.0)
        scene.fill = Color.web("#f0f0f0");
        stage.title = APP_NAME
        stage.scene = scene
        stage.show()
    }
}

fun main() {
    Application.launch(BattleShipsClient::class.java)
}