package trees

import KVPairs
import trees.nodes.BSTNode

class BinarySearchTree<K : Comparable<K>, V, KV : KVPairs<K, V>, NODE_TYPE : Node<KV, NODE_TYPE>> :
    BinaryTree<K, V, KV, NODE_TYPE>() {
    override fun add(node: NODE_TYPE) {
        recursive_add(root, node)
    }

    private fun recursive_add(current: NODE_TYPE?, node: NODE_TYPE): NODE_TYPE {
        if (root == null) {
            root = node
            return node
        }
        if (current == null)
            return node

        if (current.keyValue < node.keyValue) {
            current.right = recursive_add(current.right, node)
        } else {
            current.left = recursive_add(current.left, node)
        }
        return current
    }

    override fun delete(node: NODE_TYPE) {
        root?.let { recursive_delete(it, node) }
    }

    private fun recursive_delete(current: NODE_TYPE, node: NODE_TYPE) {
        when {
            node.keyValue > current.keyValue -> scan(node, current.right, current)
            node.keyValue < current.keyValue -> scan(node, current.left, current)
            else -> removeNode(current, null)
        }
    }

    private fun scan(node: NODE_TYPE, current: NODE_TYPE?, parent: NODE_TYPE) {
        if (current == null) {
            println("value ${node.keyValue} not exist in the tree")
            return
        }
        when {
            node.keyValue > current.keyValue -> scan(node, current.right, current)
            node.keyValue < current.keyValue -> scan(node, current.left, current)
            else -> removeNode(current, parent)
        }
    }

    private fun removeNode(node: NODE_TYPE, parent: NODE_TYPE?) {
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

    private fun removeNoChildNode(node: NODE_TYPE, parent: NODE_TYPE?) {
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

    private fun removeTwoChildNode(node: NODE_TYPE) {
        val leftChild = node.left
        if (leftChild != null) {
            leftChild.right?.let {
                val maxParent = findParentOfMaxChild(leftChild)
                maxParent.right?.let {
                    node.keyValue = it.keyValue
                    maxParent.right = null
                } ?: throw IllegalStateException("Node with max child must have the right child!")

            } ?: run {
                node.keyValue = leftChild.keyValue
                node.left = leftChild.left
            }
        }
    }

    private fun findParentOfMaxChild(n: NODE_TYPE): NODE_TYPE {
        return n.right?.let { r -> r.right?.let { findParentOfMaxChild(r) } ?: n }
            ?: throw IllegalArgumentException("Right child must be non-null")
    }

    private fun removeSingleChildNode(parent: NODE_TYPE, child: NODE_TYPE) {
        parent.keyValue = child.keyValue
        parent.left = child.left
        parent.right = child.right
    }
}