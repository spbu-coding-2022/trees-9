package trees

import trees.nodes.BSTNode

class BinarySearchTree<T : Comparable<T>> : BinaryTree<T, BSTNode<T>>() {
    override fun add(node: BSTNode<T>) {
        recursive_add(root, node)
    }

    private fun recursive_add(current: BSTNode<T>?, node: BSTNode<T>): BSTNode<T> {
        if (root == null) {
            root = node
            return node
        }
        if (current == null) {
            return node
        }
        if (current.keyValue < node.keyValue) {
            current.right = recursive_add(current.right, node)
        } else {
            current.left = recursive_add(current.left, node)
        }
        return current
    }

    override fun delete(node: BSTNode<T>) {
        root?.let { recursive_delete(it, node) }
    }

    private fun recursive_delete(current: BSTNode<T>, node: BSTNode<T>) {
        when {
            node.keyValue > current.keyValue -> scan(node, current.right, current)
            node.keyValue < current.keyValue -> scan(node, current.left, current)
            else -> removeNode(current, null)
        }
    }

    private fun scan(node: BSTNode<T>, current: BSTNode<T>?, parent: BSTNode<T>) {
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

    private fun removeNode(node: BSTNode<T>, parent: BSTNode<T>?) {
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

    private fun removeNoChildNode(node: BSTNode<T>, parent: BSTNode<T>?) {
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

    private fun removeTwoChildNode(node: BSTNode<T>) {
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

    private fun findParentOfMaxChild(n: BSTNode<T>): BSTNode<T> {
        return n.right?.let { r -> r.right?.let { findParentOfMaxChild(r) } ?: n }
            ?: throw IllegalArgumentException("Right child must be non-null")
    }

    private fun removeSingleChildNode(parent: BSTNode<T>, child: BSTNode<T>) {
        parent.keyValue = child.keyValue
        parent.left = child.left
        parent.right = child.right
    }
}