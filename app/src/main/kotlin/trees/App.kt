package trees

import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import trees.gui.screens.homeScreen

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Trees by ",
        state = rememberWindowState(width = 1200.dp, height = 900.dp)
    ) {
        homeScreen()
//        BSTScreen()
    }
}   