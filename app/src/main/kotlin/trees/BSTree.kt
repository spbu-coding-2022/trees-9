package trees

import trees.abstract_trees.BinaryTree
import trees.nodes.BSNode

class BSTree<K : Comparable<K>, V> : BinaryTree<K, V, BSNode<K, V>>() {
    fun isBstOk(node: BSNode<K, V>? = root): Boolean {
        if (node == null) {
            return true
        }
        // `compare to` returns not null value
        if (node.left != null && node.left!!.key > node.key) {
            return false
        }
        if (node.right != null && node.right!!.key < node.key) {
            return false
        }
        return !(!isBstOk(node.right) || !isBstOk(node.left))
    }

    override fun add(key: K, value: V) {
        root = recursiveAdd(root, BSNode(key, value))
    }

    private fun recursiveAdd(currentNode: BSNode<K, V>?, node: BSNode<K, V>): BSNode<K, V> {
        if (currentNode == null) return node
        if (currentNode.key < node.key) {
            currentNode.right = recursiveAdd(currentNode.right, node)
        }
        if (currentNode.key > node.key) {
            currentNode.left = recursiveAdd(currentNode.left, node)
        }
        return currentNode
    }

    override fun remove(key: K) {
        val node = find(key)
        root = node?.let { recursiveRemove(root, it) }
    }

    private fun recursiveRemove(currentNode: BSNode<K, V>?, node: BSNode<K, V>): BSNode<K, V>? {
        if (currentNode == null) return null
        if (currentNode.key < node.key) {
            currentNode.right = recursiveRemove(currentNode.right, node)
        } else if (currentNode.key > node.key) {
            currentNode.left = recursiveRemove(currentNode.left, node)
        } else {
            if (currentNode.left == null || currentNode.right == null)
                return currentNode.left ?: currentNode.right
            val minRightSubtree = getMinNode(currentNode.right!!)
            currentNode.key = minRightSubtree.key
            currentNode.value = minRightSubtree.value
            currentNode.right = recursiveRemove(currentNode.right, minRightSubtree)
        }
        return currentNode
    }
}
