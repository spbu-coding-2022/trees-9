package trees

import trees.abstract_trees.BinaryTree
import trees.nodes.BSNode

class BSTree<K : Comparable<K>, V> : BinaryTree<K, V, BSNode<K, V>>() {
    override fun add(node: BSNode<K, V>) {
        recursive_add(root, node)
    }
    private fun recursive_add(current: BSNode<K, V>?, node: BSNode<K, V>): BSNode<K, V> {
        if (root == null) {
            root = node
            return node
        }
        if (current == null) {
            return node
        }
        if (current.key < node.key) {
            current.right = recursive_add(current.right, node)
        } else {
            current.left = recursive_add(current.left, node)
        }
        return current
    }

    override fun remove(node: BSNode<K, V>) {
        root?.let { recursive_delete(it, node) }
    }

    private fun recursive_delete(current: BSNode<K, V>, node: BSNode<K, V>) {
        when {
            node.key > current.key -> scan(node, current.right, current)
            node.key < current.key -> scan(node, current.left, current)
            else -> removeNode(current, null)
        }
    }

    private fun scan(node: BSNode<K, V>, current: BSNode<K, V>?, parent: BSNode<K, V>) {
        if (current == null) {
            println("value ${node.key} not exist in the tree")
            return
        }
        when {
            node.key > current.key -> scan(node, current.right, current)
            node.key < current.key -> scan(node, current.left, current)
            else -> removeNode(current, parent)
        }
    }

    private fun removeNode(node: BSNode<K, V>, parent: BSNode<K, V>?) {
        node.left?.let { leftChild ->
            run {
                node.right?.let {
                    removeTwoChildNode(node)
                } ?: removeSingleChildNode(node, leftChild)
            }
        } ?: run {
            node.right?.let { rightChild -> removeSingleChildNode(node, rightChild) } ?: removeNoChildNode(node, parent)
        }
    }

    private fun removeNoChildNode(node: BSNode<K, V>, parent: BSNode<K, V>?) {
        parent?.let { p ->
            if (node == p.left) {
                p.left = null
            } else if (node == p.right) {
                p.right = null
            }
        } ?: throw IllegalStateException(
            "Can not remove the root node without child nodes"
        )
    }

    private fun removeTwoChildNode(node: BSNode<K, V>) {
        val leftChild = node.left
        if (leftChild != null) {
            leftChild.right?.let {
                val maxParent = findParentOfMaxChild(leftChild)
                maxParent.right?.let {
                    node.key = it.key
                    maxParent.right = null
                } ?: throw IllegalStateException("Node with max child must have the right child!")

            } ?: run {
                node.key = leftChild.key
                node.left = leftChild.left
            }
        }
    }

    private fun findParentOfMaxChild(n: BSNode<K, V>): BSNode<K, V> {
        return n.right?.let { r -> r.right?.let { findParentOfMaxChild(r) } ?: n }
            ?: throw IllegalArgumentException("Right child must be non-null")
    }

    private fun removeSingleChildNode(parent: BSNode<K, V>, child: BSNode<K, V>) {
        parent.key = child.key
        parent.left = child.left
        parent.right = child.right
    }
}
