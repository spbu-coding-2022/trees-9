package trees.gui.screens

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import trees.BSTree
import trees.dataBases.BST.insertAllNodesToTree
import trees.dataBases.BST.removeFile
import trees.dataBases.BST.writeAllNodesToFile
import trees.gui.*
import trees.nodes.BSNode

var treeInit = false
var findMode = false
var enteredValue: String = ""
var enteredKey: String = ""

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun BSTScreen(toMenu: () -> Unit) {
    val tree = remember { mutableStateOf(BSTree<Int, String>()) }
    var screenReload by remember { mutableStateOf(false) }
    if (screenReload) {
        screenReload = false
    }
    if (!treeInit) {
        tree.value = insertAllNodesToTree()
        treeInit = !treeInit
    }
    val node = tree.value.root
    val stack = mutableListOf(node?.key)
    while (stack.isNotEmpty()) {
        val current = stack.removeLast()?.let { current -> tree.value.find(current) }
        if (current?.left != null)
            current.left?.key.apply(stack::add)
        if (current?.right != null)
            current.right?.key.apply(stack::add)
        current?.let {
            if (findMode && (enteredKey.toIntOrNull() != null) && it.key == enteredKey.toInt() && (tree.value.find(
                    enteredKey.toInt()
                ) != null)
            ) {
                printNode(it, true)
            } else {
                printNode(it)
            }
            val parent = tree.value.root?.let { it1 -> getParent(it.key, it1) }
            parent?.let { it1 ->
                if (tree.value.root != it) {
                    printLine(it1, it)
                }
            }
        }
    }
    Box {
        Column(
            Modifier.padding(10.dp, 10.dp, 0.dp, 0.dp).background(Color.Unspecified).shadow(1.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        Modifier.width(200.dp).height(150.dp).offset(0.dp, 0.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Button(
                            onClick = {
                                findMode = false
                                var coordinate = Coordinate(0f, 0f)
                                var parent = BSNode(0, "")
                                var value =
                                    settingValue(enteredValue, coordinate.x + 50.0f, coordinate.y + .0f, 0, false)
                                var curNode = BSNode(enteredKey.toInt(), value)
                                tree.value.add(curNode.key, curNode.value)
                                if (tree.value.root?.key != enteredKey.toInt()) {
                                    parent = getParent(enteredKey.toInt(), tree.value.root!!)
                                    coordinate = getCoordinate(parent.value)
                                    println("${coordinate.x} ${coordinate.y}")
                                }
                                val point = findBracketPoint(value)
                                val offset = 85f
                                value = if (parent.left?.key == enteredKey.toInt()) {
                                    settingValue(value, coordinate.x - offset, coordinate.y + offset, point, true)
                                } else {
                                    settingValue(value, coordinate.x + offset, coordinate.y + offset, point, true)
                                }
                                curNode = BSNode(enteredKey.toInt(), value)
                                tree.value.add(curNode.key, curNode.value)
                                screenReload = true
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
                                if (findMode) {
                                    findMode = false
                                }
                                tree.value.remove(enteredKey.toInt())
                                screenReload = true
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
                                findMode = !findMode
                                screenReload = true
                            }) {
                            Text(
                                text = "Find mode",
                                modifier = Modifier.width(110.dp),
                                fontSize = 16.sp,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Column(
                        Modifier.width(200.dp).height(150.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Button(
                            onClick = {
                                removeFile()
                                writeAllNodesToFile(tree.value.root, tree.value)
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
                                treeInit = !treeInit
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
                Column() {
                    Column(
                        Modifier.width(200.dp).height(260.dp).offset(25.dp, 10.dp),
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start,
                    ) {
                        val fieldsWidth = 140
                        var key by remember { mutableStateOf("") }
                        OutlinedTextField(
                            modifier = Modifier
                                .width(fieldsWidth.dp)
                                .height(65.dp)
                                .padding(vertical = 5.dp),
                            value = key,
                            onValueChange = {
                                key = it
                                enteredKey = it
                            },
                            label = { Text("Enter key", textAlign = TextAlign.Center) }
                        )
                        var value by remember { mutableStateOf("") }
                        OutlinedTextField(
                            modifier = Modifier
                                .width(fieldsWidth.dp)
                                .height(65.dp)
                                .padding(vertical = 5.dp),
                            value = value,
                            onValueChange = {
                                value = it
                                enteredValue = it
                            },
                            label = { Text("Enter value", textAlign = TextAlign.Center) }
                        )
                    }
                }
            }
        }
    }
}
