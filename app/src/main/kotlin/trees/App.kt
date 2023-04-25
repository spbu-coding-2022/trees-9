package trees

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import kotlin.system.exitProcess

@Preview
@Composable
fun HomeScreen() {
    Column(Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,) {
        Button(
            onClick = {
                TODO("TreeScreen")
            }) {
            Text(text = "Binary search tree")
        }
        Button(
            onClick = {
                TODO("TreeScreen")
            }) {
            Text(text = "AVL tree")
        }
        Button(
            onClick = {
                TODO("TreeScreen")
            }) {
            Text(text = "Red-black tree")
        }
        Button(
            onClick = {
                exitProcess(0)
            }) {
            Text(text = "Exit")
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

//val bst = insertAllNodesToTree()
//        var x = 1
//        val stack = mutableListOf(bst.root?.key)
//        while (stack.isNotEmpty()) {
//            val current = stack.removeLast()?.let { current -> bst.find(current) }
//            if (current?.left != null)
//                current.left?.key.apply(stack::add)
//            if (current?.right != null)
//                current.right?.key.apply(stack::add)
//            Text("Node $x - ${current?.key}, ${current?.value}\n")
//            x++
//        }