package trees.abstract_trees

import trees.nodes.Node
import trees.nodes.RBNode

abstract class BinaryTree<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>> {
    protected var root: NODE_TYPE? = null

    fun find(key: K): NODE_TYPE? {
        var current = root
        while (current != null && key != current.key) {
            current = when {
                current.key < key -> current.right
                current.key > key -> current.left
                else -> current
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

    protected fun getMinNode(node: NODE_TYPE): NODE_TYPE {
        var minimal = node
        while (true) {
            minimal = minimal.left ?: break
        }
        return minimal
    }

    abstract fun add(key: K, value: V)
    abstract fun remove(key: K)
}
