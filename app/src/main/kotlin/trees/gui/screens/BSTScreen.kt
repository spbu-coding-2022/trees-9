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
import trees.gui.printLine

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
        }
        if (parent != null && (parent.left == node)) {
            newXY(node, getX(parent) - size, getY(parent) + size)
        }
        parent?.let { printLine(it, node) }
        printNode(node)
        drawTree(node.left, node, size)
        drawTree(node.right, node, size)
    }
}

@Composable
fun BSTScreen(toMenu: () -> Unit) {
    val tree = insertAllNodesToTree()
//    tree.root?.left?.left = BSNode(73, "633.625;481.875;875;kkdks")
//    tree.root?.left?.left?.right = BSNode(74, "633.625;481.875;875;kkdks")
//    tree.root?.right?.right = BSNode(3465, "222.388;486.055;18;1asdf")
//    tree.root?.right?.right?.left = BSNode(3464, "222.388;486.055;18;1asdf")
//    tree.root?.right?.left = BSNode(3457, "222.388;486.055;18;1asdf")
//    tree.root?.right?.left?.right = BSNode(3458, "222.388;486.055;18;1asdf")
//    tree.root?.right?.left?.right?.right = BSNode(4500, "222.388;486.055;18;1asdf")
    drawTree(tree.root, null, 70)

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