package trees

import trees.abstract_trees.BalanceTree
import trees.nodes.AVLNode
import kotlin.math.max

class AVLTree<K : Comparable<K>, V> : BalanceTree<K, V, AVLNode<K, V>>() {

    override fun add(key: K, value: V) {
        root = recursiveAdd(root, AVLNode(key, value))
        updateHeight(root)
    }

    private fun recursiveAdd(currentNode: AVLNode<K, V>?, node: AVLNode<K, V>): AVLNode<K, V> {
        if (currentNode == null) return node
        if (currentNode.key < node.key) {
            currentNode.right = recursiveAdd(currentNode.right, node)
        }
        if (currentNode.key > node.key) {
            currentNode.left = recursiveAdd(currentNode.left, node)
        }
        if (currentNode.key == node.key) {
            return node
        }
        return balance(currentNode)
    }

    override fun remove(key: K) {
        val node = find(key)
        root = node?.let { recursiveRemove(root, it) }
        updateHeight(root)
    }

    private fun recursiveRemove(currentNode: AVLNode<K, V>?, node: AVLNode<K, V>): AVLNode<K, V>? {
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
        return balance(currentNode)
    }

    override fun balance(node: AVLNode<K, V>, afterRemove: Boolean?): AVLNode<K, V> {
        updateHeight(node)
        when (getBalanceFactor(node)) {
            -2 -> {
                if (getBalanceFactor(node.left) == 1) {
                    node.left = node.left?.let { rotateLeft(it) }
                    updateHeight(node.left)
                }
                val balancedNode = rotateRight(node)
                updateHeight(balancedNode)
                return balancedNode
            }

            2 -> {
                if (getBalanceFactor(node.right) == -1) {
                    node.right = node.right?.let { rotateRight(it) }
                    updateHeight(node.right)
                }
                val balancedNode = rotateLeft(node)
                updateHeight(balancedNode)
                return balancedNode
            }
        }
        return node
    }

    internal fun getHeight(node: AVLNode<K, V>?): Int {
        return node?.height ?: -1
    }

    private fun getBalanceFactor(node: AVLNode<K, V>?): Int {
        if (node == null) return 0
        return getHeight(node.right) - getHeight(node.left)
    }

    private fun updateHeight(node: AVLNode<K, V>?) {
        node?.let { it.height = max(getHeight(it.left), getHeight(it.right)) + 1 }
    }

    private fun rotateRight(node: AVLNode<K, V>): AVLNode<K, V> {
        val leftChild =
            node.left ?: throw IllegalArgumentException("When turning right, the node must have a child on the right.")
        val rightGrandChild = leftChild.right
        leftChild.right = node
        node.left = rightGrandChild
        updateHeight(leftChild.right)
        return leftChild
    }

    private fun rotateLeft(node: AVLNode<K, V>): AVLNode<K, V> {
        val rightChild =
            node.right ?: throw IllegalArgumentException("When turning right, the node must have a child on the right.")
        val leftGrandChild = rightChild.left
        rightChild.left = node
        node.right = leftGrandChild
        updateHeight(rightChild.left)
        return rightChild
    }
}
