package trees

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import trees.dataBases.BST.insertAllNodesToTree
import trees.dataBases.BST.removeFile
import trees.dataBases.BST.writeAllNodesToFile
import trees.nodes.BSNode
import kotlin.math.roundToInt
import kotlin.system.exitProcess

@Preview
@Composable
fun HomeScreen() {
    val fontsize = 22
    val buttonWidth = 225
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                TODO("TreeScreen")
//                BSTScreen()
            }) {
            Text(
                text = "Binary search tree",
                modifier = Modifier.width(buttonWidth.dp),
                fontSize = fontsize.sp,
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = {
                TODO("TreeScreen")
            }) {
            Text(
                text = "AVL tree",
                modifier = Modifier.width(buttonWidth.dp),
                fontSize = fontsize.sp,
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = {
                TODO("TreeScreen")
            }) {
            Text(
                text = "Red-black tree",
                modifier = Modifier.width(buttonWidth.dp),
                fontSize = fontsize.sp,
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = {
                exitProcess(0)
            }) {
            Text(
                text = "Exit",
                modifier = Modifier.width(buttonWidth.dp),
                fontSize = fontsize.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

fun settingValue(value: String, x: Float, y: Float, isNotFirst: Boolean): String {
    val roundedX = (x * 1000).roundToInt() / 1000.0f
    val roundedY = (y * 1000).roundToInt() / 1000.0f
    if (isNotFirst) {
        val pointsLenSize = roundedX.toString().length + roundedY.toString().length + 2
        return roundedX.toString() + ';' + roundedY.toString() + ';' + value.slice(pointsLenSize until value.length)
    } else {
        return "$roundedX;$roundedY;$value"
    }
}

@Composable
fun BSTScreen() {
    val tree = insertAllNodesToTree()
    val node = tree.root
    val stack = mutableListOf(node?.key)
    while (stack.isNotEmpty()) {
        val current = stack.removeLast()?.let { current -> tree.find(current) }
        if (current?.left != null)
            current.left?.key.apply(stack::add)
        if (current?.right != null)
            current.right?.key.apply(stack::add)
        current?.let { printNode(it) }
    }

    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(
            onClick = {
                removeFile()
                writeAllNodesToFile(tree.root, tree)
            }) {
            Text(
                text = "Save tree",
                modifier = Modifier.width(250.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = {
                removeFile()
            }) {
            Text(
                text = "Remove BD file",
                modifier = Modifier.width(250.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
fun printNode(node: BSNode<Int, String>) {
    var p = 0
    var p1 = 0
    for (i in 0 until node.value.length) {
        if (node.value[i] == ';') {
            p = i
            break
        }
    }
    for (i in p + 1 until node.value.length) {
        if (node.value[i] == ';') {
            p1 = i
            break
        }
    }
    val x = remember { mutableStateOf((node.value.slice(0 until p).toFloat()).dp) }
    val y = remember { mutableStateOf((node.value.slice(p + 1 until p1).toFloat()).dp) }
    Box(modifier = Modifier.offset((x.value), (y.value))) {
        Box(
            modifier = Modifier
                .requiredSize(54.dp)
                .clip(CircleShape)
                .background(Color.Red)
                .pointerInput(x, y) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        x.value += dragAmount.x.toDp()
                        y.value += dragAmount.y.toDp()
                        node.value = settingValue(node.value, x.value.value, y.value.value, true)
                    }
                },
            contentAlignment = Alignment.Center
        ) {
            Text("${node.key}")
        }
    }
}

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Trees by ",
        state = rememberWindowState(width = 1200.dp, height = 900.dp)
    ) {
        HomeScreen()
//        BSTScreen()
    }
}   