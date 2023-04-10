package trees.abstract_trees

import trees.nodes.Node

abstract class BinaryTree<K: Comparable<K>, V, NODE_TYPE: Node<K, V, NODE_TYPE>> {
    protected var root: NODE_TYPE? = null

    fun find(node: NODE_TYPE): NODE_TYPE? {
        var current = root
        while (current != null && node != current) {
            current = when {
                current.key < node.key -> current.right
                current.key > node.key -> current.left
                else -> node
            }
        }
        return current
    }

    private fun recursivePrint(prefix: String, node: NODE_TYPE?, isLeft: Boolean) {
        if (node == null) {
            return
        }
        println("$prefix└──${node.key}")
        var newPrefix = "$prefix    "
        if (isLeft) {
            newPrefix = "$prefix│   "
        }
        recursivePrint(newPrefix, node.left, true)
        recursivePrint(newPrefix, node.right, false)
    }

    fun print() {
        recursivePrint("", root, false)
    }

    abstract fun add(node: NODE_TYPE)
    abstract fun remove(node: NODE_TYPE)
}
