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
    private lateinit var letterButtons: List<Button>

    @FXML
    fun initialize() {
         letterButtons = keyboardRoot.children
            .filterIsInstance<HBox>()
            .flatMap { row ->
                row.children
                    .filterIsInstance<Button>()
                    .filter { isLetterButton(it) }
            }
    }

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
        val shiftButton = event.source as Button
        if (shifted) {
            shiftButton.styleClass.remove("shift-active")
        } else {
            shiftButton.styleClass.add("shift-active")
        }

        for (key in letterButtons) {
            key.text = if (shifted) {
                key.text.lowercase()
            } else {
                key.text.uppercase()
            }
        }

        shifted = !shifted

    }

    private fun isLetterButton(button: Button): Boolean {
        return (button.text.length == 1 && button.text[0].isLetter())
    }
}
