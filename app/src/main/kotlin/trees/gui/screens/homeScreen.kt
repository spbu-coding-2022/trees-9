package trees.gui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.system.exitProcess

@Composable
fun homeScreen(
    toBST: () -> Unit,
    toAVL: () -> Unit,
    toRB: () -> Unit
) {
    val fontsize = 22
    val buttonWidth = 225
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Button(onClick = toBST) {
            Text(
                text = "Binary search tree",
                modifier = Modifier.width(buttonWidth.dp),
                fontSize = fontsize.sp,
                textAlign = TextAlign.Center
            )
        }
        Button(onClick = toAVL) {
            Text(
                text = "AVL tree",
                modifier = Modifier.width(buttonWidth.dp),
                fontSize = fontsize.sp,
                textAlign = TextAlign.Center
            )
        }
        Button(onClick = toRB) {
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
