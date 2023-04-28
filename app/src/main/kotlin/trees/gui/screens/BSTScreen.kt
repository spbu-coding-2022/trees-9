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
import trees.dataBases.BST.insertAllNodesToTree
import trees.dataBases.BST.removeFile
import trees.dataBases.BST.writeAllNodesToFile
import trees.gui.printNode

@Composable
fun BSTScreen(toMenu: () -> Unit) {
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
                modifier = Modifier.width(150.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = {
                removeFile()
            }) {
            Text(
                text = "Clear DB file",
                modifier = Modifier.width(150.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
        Button(
            onClick = {
                    toMenu()
            }) {
            Text(
                text = "Back to menu",
                modifier = Modifier.width(150.dp),
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}
