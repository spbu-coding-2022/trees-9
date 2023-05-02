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
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import trees.nodes.BSNode
import kotlin.math.roundToInt

fun getParent(key: Int, rootNode: BSNode<Int, String>): BSNode<Int, String> {
    var current: BSNode<Int, String> = rootNode
    while (current.left?.key != key && current.right?.key != key) {
        println("${current.key} $key ${current.right?.key} ${current.left?.key}")
        current = when {
            current.key < key -> current.right!!
            current.key > key -> current.left!!
            else -> return current
        }
    }
    return current
}

fun findBracketPoint(value: String): Int {
    var count = 0
    for (i in value.indices) {
        if (value[i] == ';') {
            count++
        }
        if (count == 2) {
            return i
        }
    }
    return 0
}

@Composable
fun printLine(start: BSNode<Int, String>, end: BSNode<Int, String>) {
    val coorSt = getCoordinate(start.value)
    val x0 = coorSt.x.dp
    val y0 = coorSt.y.dp
    val coorEn = getCoordinate(end.value)
    val x1 = coorEn.x.dp
    val y1 = coorEn.y.dp
    Box(modifier = Modifier.offset(x0 / 2 + 13.dp, y0 / 2 + 10.dp)) {
        Box(modifier = Modifier.offset(x0 / 2 + 13.dp, y0 / 2 + 10.dp)) {
            Box(
                modifier = Modifier.size(x1 - x0, y1 - y0)
                    .drawBehind { drawLine(Color.Black, Offset.Zero, Offset((x1 - x0).toPx(), (y1 - y0).toPx()), 1f) }
            )
        }
    }
}

class Coordinate(
    var x: Float,
    var y: Float
)

fun getCoordinate(value: String): Coordinate {
    var pointerToBracket1 = 0
    var pointerToBracket2 = 0
    for (i in value.indices) {
        if (value[i] == ';') {
            pointerToBracket1 = i
            break
        }
    }
    for (i in pointerToBracket1 + 1 until value.length) {
        if (value[i] == ';') {
            pointerToBracket2 = i
            break
        }
    }
    val x = value.slice(0 until pointerToBracket1).toFloat()
    val y = value.slice(pointerToBracket1 + 1 until pointerToBracket2).toFloat()
    return Coordinate(x, y)
}

fun settingValue(value: String, x: Float, y: Float, pointerToValue: Int, isNotFirst: Boolean): String {
    val roundedX = (x * 1000).roundToInt() / 1000.0f
    val roundedY = (y * 1000).roundToInt() / 1000.0f
    if (isNotFirst) {
        return roundedX.toString() + ';' + roundedY.toString() + ';' + value.slice(pointerToValue + 1 until value.length)
    } else {
        return "$roundedX;$roundedY;$value"
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun printNode(node: BSNode<Int, String>, difColour: Boolean = false) {
    var p1 = 0
    var p2 = 0
    for (i in node.value.indices) {
        if (node.value[i] == ';') {
            p1 = i
            break
        }
    }
    for (i in p1 + 1 until node.value.length) {
        if (node.value[i] == ';') {
            p2 = i
            break
        }
    }
    val coordinate = getCoordinate(node.value)
    val x = remember(node.key) { mutableStateOf((coordinate.x).dp) }
    val y = remember(node.key) { mutableStateOf((coordinate.y).dp) }
    val valueToPrint = node.value.slice(p2 + 1 until node.value.length)
    val color = if (difColour) {
        Color.Green
    } else {
        Color.Red
    }
    Box(modifier = Modifier.offset((x.value), (y.value))) {
        Box(
            modifier = Modifier
                .requiredSize(54.dp)
                .clip(CircleShape)
                .background(color)
                .pointerInput(x, y) {
                    detectDragGestures { change, dragAmount ->
                        change.consume()
                        x.value += dragAmount.x.toDp()
                        y.value += dragAmount.y.toDp()
                    }
                }
                .onPointerEvent(eventType = PointerEventType.Release) {
                    node.value = settingValue(node.value, x.value.value, y.value.value, p2, true)
                },
            contentAlignment = Alignment.Center
        ) {
            Text("${node.key}")
        }
    }
}
