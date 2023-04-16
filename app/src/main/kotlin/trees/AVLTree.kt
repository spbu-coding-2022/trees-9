package trees

import trees.abstract_trees.BalanceTree
import trees.nodes.AVLNode
import kotlin.math.max

class AVLTree<K: Comparable<K>, V> : BalanceTree<K, V, AVLNode<K, V>>() {
    override fun balance(node: AVLNode<K, V>): AVLNode<K, V>? {
        updateHeight(node)
        when(getBalanceFactor(node)) {
            -2 -> {
                if (getBalanceFactor(node.left) == 1) {
                    node.left = node.left.let {rotateLeft(it!!)}
                    updateHeight(node.left)
                }
                val balancedNode = rotateRight(node)
                updateHeight(balancedNode)
                return balancedNode
            }
            2 -> {
                if (getBalanceFactor(node.right) == -1) {
                    node.right = node.right.let {rotateRight(it!!)}
                    updateHeight(node.right)
                }
                val balancedNode = rotateLeft(node)
                updateHeight(balancedNode)
                return balancedNode
            }
        }
        return node
    }

    override fun add(node: AVLNode<K, V>) {
        root = recursive_add(root, node)
        updateHeight(root!!)
    }

    private fun recursive_add(current_node: AVLNode<K,V>?, node: AVLNode<K,V>): AVLNode<K,V>? {
        if (current_node == null) return node
        if (current_node.key < node.key) {
            current_node.right = recursive_add(current_node.right, node)
        }
        if (current_node.key > node.key) {
            current_node.left = recursive_add(current_node.left, node)
        }
        return balance(current_node)
    }

    override fun remove(node: AVLNode<K, V>) {
        root = recursive_remove(root, node)
        updateHeight(root)
    }

    private fun recursive_remove(current_node: AVLNode<K, V>?, node: AVLNode<K, V>): AVLNode<K, V>? {
        if (current_node == null) return null
        if (current_node.key < node.key) {
            current_node.right = recursive_remove(current_node.right, node)
        } else if (current_node.key > node.key) {
            current_node.left = recursive_remove(current_node.left, node)
        } else {
            if (current_node.left == null || current_node.right == null)
                return current_node.left ?: current_node.right
            val minRightSubtree = getMinNode(current_node.right!!)
            current_node.key = minRightSubtree.key
            current_node.value = minRightSubtree.value
            current_node.right = recursive_remove(current_node.right, minRightSubtree)
        }
        return balance(current_node)
    }

    private fun getHeight(node: AVLNode<K, V>?): Int {
        return node?.height ?: -1
    }

    private fun getBalanceFactor(node: AVLNode<K, V>?): Int {
        if (node == null) return 0
        return getHeight(node.right) - getHeight(node.left)
    }

    private fun updateHeight(node: AVLNode<K, V>?) {
        node?.let {it.height = max(getHeight(it.left), getHeight(it.right)) + 1}
    }

    private fun rotateRight(node: AVLNode<K, V>): AVLNode<K, V> {
        val leftChild = node.left ?: throw IllegalArgumentException("When turning right, the node must have a child on the right.")
        val rightGrandChild = leftChild.right
        leftChild.right = node
        node.left = rightGrandChild
        updateHeight(leftChild.right)
        return leftChild
    }

    private fun rotateLeft(node: AVLNode<K, V>): AVLNode<K, V> {
        val rightChild = node.right ?: throw IllegalArgumentException("When turning right, the node must have a child on the right.")
        val leftGrandChild = rightChild.left
        rightChild.left = node
        node.right = leftGrandChild
        updateHeight(rightChild.left)
        return rightChild
    }
}
