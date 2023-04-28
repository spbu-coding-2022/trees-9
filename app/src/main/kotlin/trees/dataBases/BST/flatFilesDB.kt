package trees.dataBases.BST

import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.csv.CSVPrinter
import trees.BSTree
import trees.nodes.BSNode
import java.io.File

const val fileName = "./app/src/main/kotlin/trees/dataBases/BST/flatFile"
val curFile = File(fileName)

fun checkIfFileExist(file: File): Boolean {
    return file.exists()
}

fun removeFile(file: File = curFile): Boolean {
    if (checkIfFileExist(file)) {
        var result = file.delete()
        if (result) {
            result = file.createNewFile()
            if (result) {
                return result
            }
            throw Exception("Fail with file create")
        }
        throw Exception("Fail with file remove")
    }
    throw Exception("File with tree not exist")
}

fun writeAllNodesToFile(node: BSNode<Int, String>?, tree: BSTree<Int, String>, file: File = File(fileName)) {
    val stack = mutableListOf(node?.key)
    file.bufferedWriter().use {
        val csvPrinter = CSVPrinter(it, CSVFormat.DEFAULT)
        while (stack.isNotEmpty()) {
            val current = stack.removeLast()?.let {current -> tree.find(current) }
            if (current?.left != null)
                current.left?.key.apply(stack::add)
            if (current?.right != null)
                current.right?.key.apply(stack::add)
            csvPrinter.printRecord("${current?.key}","${current?.value}")
        }
    }
}

fun insertAllNodesToTree(file: File = curFile): BSTree<Int, String> {
    val tree = BSTree<Int, String>()
    file.bufferedReader ().use {
        val csvParser = CSVParser(it, CSVFormat.DEFAULT)
        for(csvRecord in csvParser) {
            val nodeKey = csvRecord.get(0)
            val nodeValue = csvRecord.get(1)
            tree.add(nodeKey.toInt(), nodeValue)
        }
    }
    return tree
}
