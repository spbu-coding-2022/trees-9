package trees

import trees.nodes.Node

abstract class BinaryTree<T : Comparable<T>, NODE_TYPE : Node<T, NODE_TYPE>> {
    protected var root: NODE_TYPE? = null

    private fun recursive_find(node: NODE_TYPE, searchedNode: NODE_TYPE): NODE_TYPE? = when {
        node.keyValue > searchedNode.keyValue -> node.left?.let { recursive_find(it, searchedNode) }
        node.keyValue < searchedNode.keyValue -> node.right?.let { recursive_find(it, searchedNode) }
        else -> node
    }

    fun find(searchedNode: NODE_TYPE): Boolean {
        val a = root?.let { recursive_find(it, searchedNode) }
        return a?.keyValue != searchedNode.keyValue
    }

    abstract fun add(node: NODE_TYPE)
    abstract fun delete(node: NODE_TYPE)
}