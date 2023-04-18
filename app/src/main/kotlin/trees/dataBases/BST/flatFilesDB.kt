package trees.dataBases.BST

import trees.BSTree
import trees.nodes.BSNode
import java.io.File

const val fileName = "./app/src/main/kotlin/trees/dataBases/BST/flatFile"
val file = File(fileName)

fun checkIfFileExist(file: File): Boolean {
    return file.exists()
}

fun writeAllNodesToFile(node: BSNode<Int, String>?, tree: BSTree<Int, String>, file: File) {
    val stack = mutableListOf(node?.key)
    var stringToWrite: String
    while (stack.isNotEmpty()) {
        val current = stack.removeLast()?.let { tree.find(it) }
        if (current?.left != null)
            current.left?.key.apply(stack::add)
        if (current?.right != null)
            current.right?.key.apply(stack::add)
        stringToWrite = "${current?.key}${current?.value} 0 0\n"
        file.appendText(stringToWrite)
    }
}

fun insertAllNodesToTree(tree: BSTree<Int, String>, file: File) {
    file.forEachLine {
        val inputs = it.split(" ").toTypedArray()
        tree.add(inputs[0].toInt(),inputs[1])
    }
}