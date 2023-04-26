package trees.gui

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp

@Composable
fun Node(
    key: Int,
    value: String?,
    x: Double,
    y: Double
) {
    val x = remember { mutableStateOf(x.dp) }
    val y = remember { mutableStateOf(y.dp) }
    Box(modifier = Modifier.offset((x.value), (y.value))) {
        Box(modifier = Modifier
            .requiredSize(54.dp)
            .clip(CircleShape)
            .background(Color.Red)
            .pointerInput(x, y) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    x.value += dragAmount.x.toDp()
                    y.value += dragAmount.y.toDp()
                }
            },
            contentAlignment = Alignment.Center) {
            Text("$key")
        }
    }
}