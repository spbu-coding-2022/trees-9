package trees.gui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import trees.gui.screens.getX
import trees.gui.screens.getY
import trees.nodes.BSNode
import kotlin.math.roundToInt

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
fun printLine(start: BSNode<Int, String>, end: BSNode<Int, String>) {
    val x0 = getX(start).dp
    val y0 = getY(start).dp
    val x1 = getX(end).dp
    val y1 = getY(end).dp

    Box(modifier = Modifier.offset(x0, y0)) {
        Box(
            modifier = Modifier.size(x1 - x0, y1 - y0)
                .drawBehind { drawLine(Color.Black, Offset.Zero, Offset((x1 - x0).toPx(), (y1 - y0).toPx()), 4f) }
        )
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