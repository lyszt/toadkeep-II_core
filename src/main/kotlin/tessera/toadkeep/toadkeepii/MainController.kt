package tessera.toadkeep.toadkeepii

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener
import javafx.application.Platform
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ToggleButton
import javafx.scene.input.Clipboard
import javafx.scene.input.ClipboardContent
import javafx.scene.input.MouseButton
import javafx.scene.layout.VBox
import java.util.concurrent.Executors

class MainController {
    private var elementText = ""
    private var shifted = false
    private val typeExecutor = Executors.newSingleThreadExecutor { r ->
        Thread(r, "toadkeep-typer").apply { isDaemon = true }
    }
    private val missingKeys = mutableSetOf<String>()
    public lateinit var keyboardRoot: VBox
    public lateinit var keyCompleterToggle: ToggleButton
    private lateinit var letterButtons: List<Button>


    @FXML
    fun initialize() {
        Keymap.initialize(keyboardRoot)
        letterButtons = Keymap.letterButtons(keyboardRoot)
        for (button in letterButtons) {
            button.setOnMouseClicked { event ->
                if (event.button == MouseButton.SECONDARY) toggleMissing(button)
            }
        }
        GlobalScreen.registerNativeHook()
        GlobalScreen.addNativeKeyListener(object: NativeKeyListener {
            override fun nativeKeyPressed(nativeEvent: NativeKeyEvent) {
                val key = NativeKeyEvent.getKeyText(nativeEvent.keyCode)

                if(nativeEvent.keyCode == NativeKeyEvent.VC_PAGE_DOWN) {
                    keyCompleterToggle.selectedProperty().set(!keyCompleterToggle.isSelected)
                }
                val match = Keymap.keyMap.flatten()
                    .firstOrNull { it.equals(key, ignoreCase = true) } ?: return
                Platform.runLater { handleKeyType(match) }
            }
            override fun nativeKeyReleased(e: NativeKeyEvent) { }
            override fun nativeKeyTyped(e: NativeKeyEvent) { }
        })
    }

    @FXML
    private fun onKeyboardButtonClick(event: ActionEvent) {
        val button = event.source as Button
        handleKeyCopy(button.text)
    }

    private fun toggleMissing(button: Button) {
        val key = button.text.lowercase()
        if (missingKeys.remove(key)) {
            button.styleClass.remove("missing-key")
        } else {
            missingKeys.add(key)
            button.styleClass.add("missing-key")
        }
    }

    private fun handleKeyCopy(key: String) {
        elementText = when (key) {
            "Space" -> " "
            else -> if (shifted) key.uppercase() else key.lowercase()
        }

        val middleKey = if (keyCompleterToggle.isSelected) Keymap.push(key) else null
        if (middleKey != null) {
            if(shifted) copyContent(middleKey.uppercase()) else copyContent(middleKey.lowercase())
        }
        else {
            copyContent(elementText)
        }
    }

    private fun handleKeyType(key: String) {
        elementText = when (key) {
            "Space" -> " "
            else -> if (shifted) key.uppercase() else key.lowercase()
        }

        val middleKey = if (keyCompleterToggle.isSelected) Keymap.push(key) else null
        if (middleKey != null && middleKey.lowercase() in missingKeys) {
            val out = if (shifted) middleKey.uppercase() else middleKey.lowercase()
            typeExecutor.submit {
                Thread.sleep(40)
                replaceSideKeysWith(out)
            }
        }

    }

    private fun replaceSideKeysWith(text: String) {
        ProcessBuilder(
            "xdotool",
            "key", "--clearmodifiers", "BackSpace", "BackSpace",
            "type", "--clearmodifiers", "--", text
        ).start().waitFor()
    }

    private fun handkeKey(key: String, type: InteractionTypes) {
        when (type) {
            InteractionTypes.COPY -> handleKeyCopy(key)
            InteractionTypes.TYPE -> handleKeyType(key)

        }
    }


    fun copyContent(text: String) {
        val clipboard = Clipboard.getSystemClipboard()
        val content = ClipboardContent()
        content.putString(text);
        clipboard.setContent(content)
    }

    fun typeContent(text: String) {
        ProcessBuilder("xdotool", "type", "--clearmodifiers", "--", text).start().waitFor()
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
