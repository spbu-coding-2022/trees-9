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
import trees.nodes.AVLNode
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

fun getAVLParent(key: Int, rootNode: AVLNode<Int, String>): AVLNode<Int, String> {
    // Functions will be merged in the future.
    var current: AVLNode<Int, String> = rootNode
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
fun printLine(start: BSNode<Int, String>, end: BSNode<Int, String>, marker: Boolean) {
    //If marker is true then draw left, else draw ridth
    val x0 = getX(start).dp
    val y0 = getY(start).dp
    val x1 = getX(end).dp
    val y1 = getY(end).dp

    if (marker == true) {
        Box(modifier = Modifier.offset(x0 + (0.4 * 25).dp, y0 + ((1.82*25).dp))) {
            Box(
                modifier = Modifier.size(x1 - x0, y1 - y0)
                    .drawBehind { drawLine(Color.Black, Offset.Zero, Offset((x1 - x0).toPx(), (y1 - y0).toPx()), 4f) }
            )
        }
    } else {
        Box(modifier = Modifier.offset(x0 + (1.82*25).dp, y0 + ((1.82*25).dp))) {
            Box(
                modifier = Modifier.size(x1 - x0, y1 - y0)
                    .drawBehind { drawLine(Color.Black, Offset.Zero, Offset((x1 - x0).toPx(), (y1 - y0).toPx()), 4f) }
            )
        }
    }
}

@Composable
fun printLine(start: AVLNode<Int, String>, end: AVLNode<Int, String>, marker: Boolean) {
    // Functions will be merged in the future.

    //If marker is true then draw left, else draw ridth
    val x0 = getX(start).dp
    val y0 = getY(start).dp
    val x1 = getX(end).dp
    val y1 = getY(end).dp

    if (marker == true) {
        Box(modifier = Modifier.offset(x0 + (0.4 * 25).dp, y0 + ((1.82*25).dp))) {
            Box(
                modifier = Modifier.size(x1 - x0, y1 - y0)
                    .drawBehind { drawLine(Color.Black, Offset.Zero, Offset((x1 - x0).toPx(), (y1 - y0).toPx()), 4f) }
            )
        }
    } else {
        Box(modifier = Modifier.offset(x0 + (1.82*25).dp, y0 + ((1.82*25).dp))) {
            Box(
                modifier = Modifier.size(x1 - x0, y1 - y0)
                    .drawBehind { drawLine(Color.Black, Offset.Zero, Offset((x1 - x0).toPx(), (y1 - y0).toPx()), 4f) }
            )
        }
    }
}

fun getX(node: BSNode<Int, String>): Float {
    return node.value.split(";")[0].toFloat()
}

fun getY(node: BSNode<Int, String>): Float {
    return node.value.split(";")[1].toFloat()
}

fun getX(node: AVLNode<Int, String>): Float {
    // Functions will be merged in the future.
    return node.value.split(";")[0].toFloat()
}

fun getY(node: AVLNode<Int, String>): Float {
    // Functions will be merged in the future.
    return node.value.split(";")[1].toFloat()
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
        Color.LightGray
    }
    Box(modifier = Modifier.offset((x.value), (y.value))) {
        Box(
            modifier = Modifier
                .requiredSize(54.dp)
                .clip(CircleShape)
                .background(color)
                .onPointerEvent(eventType = PointerEventType.Release) {
                    node.value = settingValue(node.value, x.value.value, y.value.value, p2, true)
                },
            contentAlignment = Alignment.Center
        ) {
            Text("${node.key}")
        }
    }
}
