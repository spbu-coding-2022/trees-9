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

    protected fun recursive_add(current_node: AVLNode<K,V>?, node: AVLNode<K,V>): AVLNode<K,V>? {
        if (current_node == null) return node
        if (current_node.key < node.key) {
            current_node.right = recursive_add(current_node.right, node)
            updateHeight(current_node)
        }
        if (current_node.key > node.key) {
            current_node.left = recursive_add(current_node.left, node)
            updateHeight(current_node)
        }
        return balance(current_node)
    }

    override fun remove(node: AVLNode<K, V>) {
        TODO("Not yet implemented")
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

    fun rotateRight(node: AVLNode<K, V>): AVLNode<K, V> {
        val leftChild = node.left ?: throw IllegalArgumentException("When turning right, the node must have a child on the right.")
        val rightGrandChild = leftChild.right
        leftChild.right = node
        node.left = rightGrandChild
        updateHeight(leftChild.right)
        return leftChild
    }

    fun rotateLeft(node: AVLNode<K, V>): AVLNode<K, V> {
        val rightChild = node.right ?: throw IllegalArgumentException("When turning right, the node must have a child on the right.")
        val leftGrandChild = rightChild.left
        rightChild.left = node
        node.right = leftGrandChild
        updateHeight(rightChild.left)
        return rightChild
    }
}
