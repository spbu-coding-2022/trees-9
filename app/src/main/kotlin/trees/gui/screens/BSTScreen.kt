package trees.gui.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.TextField
import androidx.compose.ui.draw.clip
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import trees.BSTree
import trees.dataBases.BST.insertAllNodesToTree
import trees.dataBases.BST.removeFile
import trees.dataBases.BST.writeAllNodesToFile
import trees.gui.*
import trees.gui.printNode
import androidx.compose.ui.layout.layout
import androidx.compose.ui.modifier.modifierLocalMapOf
import trees.gui.printLine
import trees.nodes.BSNode

fun getX(node: BSNode<Int, String>): Float {
    return node.value.split(";")[0].toFloat()
}

fun getY(node: BSNode<Int, String>): Float {
    return node.value.split(";")[1].toFloat()
}

fun newXY(node: BSNode<Int, String>, x: Float, y: Float) {
    var count = 0
    var value = ""
    for(i in 0..node.value.length - 1) {
        if (node.value[i] == ';') {
            count++
        }
        if (count == 2) {
            value += node.value[i]
        }
    }
    node.value = x.toString() + ";" + y.toString() + ";" + value
}

@Composable
fun drawTree(node: BSNode<Int, String>?, parent: BSNode<Int, String>?, size: Int) {
    if (node != null) {
        if (parent != null && (parent.right == node)) {
            newXY(node, getX(parent)+ size, getY(parent) + size)
            parent?.let { printLine(it, node, false) } // Marker == false, because we draw right line
        }
        if (parent != null && (parent.left == node)) {
            newXY(node, getX(parent) - size, getY(parent) + size)
            parent?.let { printLine(it, node, true) } // Marker == true, because we draw left line
        }
        printNode(node)
        drawTree(node.left, node, size)
        drawTree(node.right, node, size)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BSTScreen(toMenu: () -> Unit) {
    val tree = insertAllNodesToTree()
    drawTree(tree.root, null, 70)

    Box() {
        Column(
            Modifier.padding(10.dp, 10.dp, 0.dp, 0.dp).background(Color.Unspecified).shadow(1.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Column(
                Modifier.width(200.dp).height(290.dp).offset(0.dp, 0.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                var key by remember { mutableStateOf("") }
                TextField(
                    modifier = Modifier.width(180.dp).padding(vertical = 15.dp).clip(RoundedCornerShape(25.dp)),
                    value = key,
                    onValueChange = { key = it },
                    label = { Text("Enter key") }
                )
                var value by remember { mutableStateOf("") }
                TextField(
                    modifier = Modifier.width(180.dp).padding(bottom = 15.dp).clip(RoundedCornerShape(25.dp)),
                    value = value,
                    onValueChange = { value = it },
                    label = { Text("Enter value") }
                )
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
