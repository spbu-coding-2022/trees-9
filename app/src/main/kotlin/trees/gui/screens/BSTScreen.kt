package trees.gui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import trees.BSTree
import trees.dataBases.BST.insertAllNodesToTree
import trees.dataBases.BST.removeFile
import trees.dataBases.BST.writeAllNodesToFile
import trees.gui.printNode
import trees.nodes.BSNode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf

@Composable
fun BSTScreen(toMenu: () -> Unit) {
    val tree = insertAllNodesToTree()
//    val tree = BSTree<Int, String>()
//    tree.root = BSNode(123, "717.875;217.375;.25;dsfsafds")
//    tree.root?.left = BSNode(75, "633.625;481.875;875;kkdks")
//    tree.root?.right = BSNode(3459, "222.388;486.055;18;1asdf")
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
    Box() {
        Column(
            Modifier.padding(10.dp, 10.dp, 0.dp, 0.dp).background(Color.Unspecified).shadow(1.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Column(
                Modifier.width(200.dp).height(180.dp).offset(0.dp, 0.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(
                    onClick = {
                    },
                ) {
                    Text(
                        text = "Insert node",
                        modifier = Modifier.width(110.dp),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Button(
                    onClick = {
                    }) {
                    Text(
                        text = "Remove node",
                        modifier = Modifier.width(110.dp),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Button(
                    onClick = {
                    }) {
                    Text(
                        text = "Find node",
                        modifier = Modifier.width(110.dp),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Column(
                Modifier.width(200.dp).height(180.dp),
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
                        modifier = Modifier.width(110.dp),
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
                        modifier = Modifier.width(110.dp),
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
                        modifier = Modifier.width(110.dp),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}