package trees

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import trees.gui.navigation.Screen
import trees.gui.screens.BSTScreen
import trees.gui.screens.homeScreen

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Trees by ",
        state = rememberWindowState(width = 1200.dp, height = 900.dp)
    ) {
        var screenState by remember { mutableStateOf<Screen>(Screen.homeScreen) }
        when (val screen = screenState) {
            is Screen.homeScreen ->
                homeScreen(toBST = { screenState = Screen.BSTScreen })

            is Screen.BSTScreen ->
                BSTScreen(toMenu = { screenState = Screen.homeScreen})
        }
    }
}   