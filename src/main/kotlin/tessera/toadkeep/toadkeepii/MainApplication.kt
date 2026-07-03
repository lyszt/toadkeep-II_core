package tessera.toadkeep.toadkeepii

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.stage.Stage

class MainApplication : Application() {
    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(MainApplication::class.java.getResource("main-view-abnt2.fxml"))
        val root: Parent = fxmlLoader.load()
        val scene = Scene(root, 980.0, -1.0)
        stage.title = "ToadKepp: Helper for People with Keyboard Issues"
        stage.scene = scene
        stage.show()
    }
}
  
