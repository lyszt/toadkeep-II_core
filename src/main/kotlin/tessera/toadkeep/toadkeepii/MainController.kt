package tessera.toadkeep.toadkeepii

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox

class MainController {
    private var elementText = ""
    private var shifted = false
    public lateinit var keyboardRoot: VBox

    @FXML
    private fun onKeyboardButtonClick(event: ActionEvent) {
       val button = event.source as Button;
        elementText = when (button.text) {
            "Space" -> " "
            else -> button.text.lowercase()
        }
        val clipboard = Clipboard.getSystemClipboard()
        val content = ClipboardContent()
        content.putString(elementText);
        clipboard.setContent(content)
    }

    @FXML
    private fun onShiftButtonClick(event: ActionEvent) {

        for (row in keyboardRoot.children) {
            if (row is HBox) {
                for (key in row.children) {
                    if (key is Button && isLetterButton(key)) {
                        if (shifted) {
                            key.text = key.text.lowercase()
                        }
                        else {
                            key.text = key.text.uppercase()
                        }
                    }
                }

            }

        }

        shifted = !shifted

    }

    private fun isLetterButton(button: Button): Boolean {
        return (button.text.length == 1 && button.text[0].isLetter())
    }
}
