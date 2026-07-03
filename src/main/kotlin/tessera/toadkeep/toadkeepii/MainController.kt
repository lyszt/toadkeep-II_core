package tessera.toadkeep.toadkeepii

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ToggleButton
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.layout.VBox

class MainController {
    private var elementText = ""
    private var shifted = false
    public lateinit var keyboardRoot: VBox
    public lateinit var keyCompleterToggle: ToggleButton
    private lateinit var letterButtons: List<Button>

    @FXML
    fun initialize() {
        Keymap.initialize(keyboardRoot)
        letterButtons = Keymap.letterButtons(keyboardRoot)
    }

    @FXML
    private fun onKeyboardButtonClick(event: ActionEvent) {
       val button = event.source as Button;
        elementText = when (button.text) {
            "Space" -> " "
            else -> button.text.lowercase()
        }

        val middleKey = if (keyCompleterToggle.isSelected) Keymap.push(button.text) else null
        if (middleKey != null) {
            if(shifted) copyContent(middleKey) else copyContent(middleKey.lowercase())
        }
        else {
            copyContent(elementText)
        }
    }

    fun copyContent(text: String) {
        val clipboard = Clipboard.getSystemClipboard()
        val content = ClipboardContent()
        content.putString(text);
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


}
