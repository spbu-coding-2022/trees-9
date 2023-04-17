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

    override fun add(node: BSNode<K, V>) {
        if (root == null) {
            root = node
            return
        }
        var current = root
        var parent = current
        while (current != null) {
            parent = current
            current = when {
                current.key < node.key -> current.right
                current.key > node.key -> current.left
                else -> throw IllegalArgumentException("Node with key ${node.key} is already in the tree")
            }
        }
        if (parent!!.key < node.key) {
            parent.right = node
        } else {
            parent.left = node
        }
    }

    override fun remove(key: K) {
        val node = find(key)
        root?.let {
            node?.let { itNode ->
                if (itNode.key > it.key) scan(itNode, it.right, it)
                if (itNode.key < it.key) scan(itNode, it.left, it)
                else removeNode(it, null)
            }
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
        } ?: rootRemove()
    }

    private fun rootRemove() {
        root = null
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
