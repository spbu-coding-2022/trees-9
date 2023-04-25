package trees

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlin.system.exitProcess

@Preview
@Composable
fun HomeScreen() {
    val fontsize = 22
    val buttonWidth = 225
    Column(Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,) {
        Button(
            onClick = {
                TODO("TreeScreen")
            }) {
            Text(text = "Binary search tree",
                modifier = Modifier.width(buttonWidth.dp),
                fontSize = fontsize.sp,
                textAlign = TextAlign.Center)
        }
        Button(
            onClick = {
                TODO("TreeScreen")
            }) {
            Text(text = "AVL tree",
                modifier = Modifier.width(buttonWidth.dp),
                fontSize = fontsize.sp,
                textAlign = TextAlign.Center)
        }
        Button(
            onClick = {
                TODO("TreeScreen")
            }) {
            Text(text = "Red-black tree",
                modifier = Modifier.width(buttonWidth.dp),
                fontSize = fontsize.sp,
                textAlign = TextAlign.Center)
        }
        Button(
            onClick = {
                exitProcess(0)
            }) {
            Text(text = "Exit",
                modifier = Modifier.width(buttonWidth.dp),
                fontSize = fontsize.sp,
                textAlign = TextAlign.Center)
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Trees by ",
        state = rememberWindowState(width = 300.dp, height = 300.dp)
    ) {
            HomeScreen()
    }
}