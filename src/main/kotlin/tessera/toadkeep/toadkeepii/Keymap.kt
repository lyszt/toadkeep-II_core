package tessera.toadkeep.toadkeepii

import javafx.scene.control.Button
import javafx.scene.layout.HBox
import javafx.scene.layout.VBox
import java.util.Stack

object Keymap {
    public val contextRoll: Stack<Pair<Int, Int>> = Stack()
    public lateinit var keyMap: List<List<String>>

    fun initialize(keyboardRoot: VBox) {
        keyMap = letterButtonRows(keyboardRoot)
            .map { row -> row.map { it.text } }
    }

    fun letterButtonRows(keyboardRoot: VBox): List<List<Button>> {
        return keyboardRoot.children
            .filterIsInstance<HBox>()
            .mapNotNull { row ->
                row.children
                    .filterIsInstance<Button>()
                    .filter { isLetterButton(it) }
                    .takeIf { it.isNotEmpty() }
            }
    }

    fun letterButtons(keyboardRoot: VBox): List<Button> {
        return letterButtonRows(keyboardRoot)
            .flatten()
    }

    fun isLetterButton(button: Button): Boolean {
        return button.text.length == 1 && button.text[0].isLetter()
    }

    fun getPosition(button: Button, keyboardRoot: VBox): Pair<Int, Int>? {
       val rows = letterButtonRows(keyboardRoot)
        for (row in rows.indices) {
            val col = rows[row].indexOf(button)
            if (col == -1) {
                return Pair(row, col)
            }
        }

        return null
    }

    fun getPosition(keyMap: List<List<String>>, key: String): Pair<Int, Int>? {
        for (row in keyMap.indices) {
            val col = keyMap[row].indexOf(key)
            if (col != -1) {
                return Pair(row, col)
            }
        }

        return null
    }

    fun getKey(keyMap: List<List<String>>, position: Pair<Int, Int>): String? {
        val row = position.first
        val col = position.second

        return keyMap.getOrNull(row)?.getOrNull(col)
    }

    fun getKey(row: Int, col: Int): String? {
        return keyMap.getOrNull(row)?.getOrNull(col)
    }

    fun push(keyContent: String): String? {
        val position = getPosition(keyMap, keyContent) ?: return null

        if (contextRoll.isEmpty()) {
            contextRoll.push(position)
            return null
        }

        val first = contextRoll.last()
        val last = position
        val middleKey = first.second + 1

        if (first.first == last.first && first.second == last.second - 2) {
            contextRoll.pop()
            return getKey(first.first, middleKey)
        }
        else {
            if(contextRoll.size >= 2) {
                contextRoll.removeAt(0)
            }
            contextRoll.push(position)
        }

        return null
    }

}
