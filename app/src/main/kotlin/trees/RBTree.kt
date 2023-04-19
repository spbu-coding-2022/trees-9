package trees

import trees.abstract_trees.BalanceTree
import trees.nodes.RBNode
import trees.nodes.RBNode.Color

class RBTree<K : Comparable<K>, V> : BalanceTree<K, V, RBNode<K, V>>() {

    override fun add(key: K, value: V) {
        val node = RBNode(key, value)

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

        if (parent == null) {
            throw IllegalStateException("Only root parent can be null")
        }

        if (parent.key < node.key) {
            parent.right = node
        } else {
            parent.left = node
        }

        node.parent = parent
        balance(node, false)
    }

    override fun remove(key: K) {
        var current = root

        while (current != null && current.key != key) {
            current = if (key < current.key) {
                current.left
            } else {
                current.right
            }
        }

        if (current == null) {
            throw IllegalStateException("There is no such node with key $key in the tree")
        }

        val movedUpNode: RBNode<K, V>?
        val deletedNodeColor: Color

        val leftSibling = current.left
        val rightSibling = current.right

        if (leftSibling == null || rightSibling == null) {
            movedUpNode = removeNodeWithZeroOrOneChild(current)
            deletedNodeColor = current.color
        } else {
            val inOrderSuccessor = getMinNode(rightSibling)
            current.key = inOrderSuccessor.key
            deletedNodeColor = inOrderSuccessor.color
            movedUpNode = removeNodeWithZeroOrOneChild(inOrderSuccessor)
        }

        if (deletedNodeColor == Color.BLACK) {
            if (movedUpNode == null) {
                throw IllegalStateException("Incorrect tree structure")
            }
            balance(movedUpNode, true)
            if (movedUpNode.isTemp) {
                replaceParentsChild(movedUpNode.parent, movedUpNode, null)
            }
        }
    }

    override fun balance(node: RBNode<K, V>, afterRemove: Boolean): RBNode<K, V>? {
        if (afterRemove) {
            balanceAfterRemove(node)
        } else {
            balanceAfterAdd(node)
        }
        return null
    }

    private fun balanceAfterAdd(node: RBNode<K, V>) {
        var parent = node.parent
        if (parent == null || parent.color == Color.BLACK) {
            return
        }

        val grandparent = parent.parent
        if (grandparent == null) {
            parent.color = Color.BLACK
            return
        }

        val uncle = getUncle(parent)
        if (uncle != null && uncle.color == Color.RED) {
            parent.color = Color.BLACK
            grandparent.color = Color.RED
            uncle.color = Color.BLACK
            balanceAfterAdd(grandparent)
        } else if (parent == grandparent.left) {

            if (node == parent.right) {
                rotateLeft(parent)
                parent = node
            }

            rotateRight(grandparent)
            parent.color = Color.BLACK
            grandparent.color = Color.RED
        } else {

            if (node == parent.left) {
                rotateRight(parent)
                parent = node
            }

            rotateLeft(grandparent)
            parent.color = Color.BLACK
            grandparent.color = Color.RED
        }
    }

    private fun balanceAfterRemove(node: RBNode<K, V>) {
        if (node == root) {
            return
        }

        val parent = node.parent
        var sibling = getSibling(node) ?: throw IllegalStateException("Can't balance node with null sibling")

        if (sibling.color == Color.RED) {
            handleRedSibling(node, sibling)
            sibling = getSibling(node) ?: throw IllegalStateException("Incorrect node relations")
        }

        if (isBlackOrNull(sibling.left) && isBlackOrNull(sibling.right)) {
            sibling.color = Color.RED
            if (parent == null) {
                throw IllegalStateException("Only root can have null parent")
            }
            if (parent.color == Color.RED) {
                parent.color = Color.BLACK
            } else {
                balanceAfterRemove(parent)
            }
        } else {
            handleBlackRedSibling(node, sibling)
        }
    }

    private fun getUncle(parent: RBNode<K, V>): RBNode<K, V>? {
        val grandparent = parent.parent
        return if (grandparent?.left == parent) {
            grandparent.right
        } else if (grandparent?.right == parent) {
            grandparent.left
        } else {
            throw IllegalStateException("Node with key ${parent.key} is not a child of its parent")
        }
    }

    private fun replaceParentsChild(parent: RBNode<K, V>?, oldChild: RBNode<K, V>?, newChild: RBNode<K, V>?) {
        if (parent == null) {
            root = newChild
        } else if (parent.left == oldChild) {
            parent.left = newChild
        } else if (parent.right == oldChild) {
            parent.right = newChild
        } else {
            throw IllegalStateException("Node with key ${parent.key} is not a parent if its child")
        }

        if (newChild != null) {
            newChild.parent = parent
        }
    }

    private fun rotateRight(node: RBNode<K, V>) {
        val parent = node.parent
        val leftChild =
            node.left ?: throw IllegalStateException("Left child of node with key ${node.key} can't be null")
        val cousin = leftChild.right

        node.left = cousin

        if (cousin != null) {
            cousin.parent = node
        }

        leftChild.right = node
        node.parent = leftChild

        replaceParentsChild(parent, node, leftChild)
    }

    private fun rotateLeft(node: RBNode<K, V>) {
        val parent = node.parent
        val rightChild =
            node.right ?: throw IllegalStateException("Right child of node with key ${node.key} can't be null")
        val cousin = rightChild.left

        node.right = cousin

        if (cousin != null) {
            cousin.parent = node
        }

        rightChild.left = node

        node.parent = rightChild

        replaceParentsChild(parent, node, rightChild)
    }

    private fun removeNodeWithZeroOrOneChild(node: RBNode<K, V>): RBNode<K, V>? {
        return if (node.left != null) {
            replaceParentsChild(node.parent, node, node.left)
            node.left
        } else if (node.right != null) {
            replaceParentsChild(node.parent, node, node.right)
            node.right
        } else {
            node.isTemp = true
            var newChild: RBNode<K, V>? = node
            
            if (node.color == Color.RED) {
                newChild = null
            }
            replaceParentsChild(node.parent, node, newChild)
            newChild
        }
    }

    private fun handleRedSibling(node: RBNode<K, V>, sibling: RBNode<K, V>) {
        val parent = node.parent ?: throw IllegalStateException("Can't handle node with null parent")
        sibling.color = Color.BLACK
        parent.color = Color.RED
        if (node == parent.left) {
            rotateLeft(parent)
        } else {
            rotateRight(parent)
        }
    }

    private fun handleBlackRedSibling(node: RBNode<K, V>?, sibling: RBNode<K, V>?) {
        val parent = node?.parent
        if (node == null || sibling == null || parent == null) {
            throw IllegalStateException("Can't handle null nodes")
        }

        var mutableSibling = sibling
        val nodeIsLeftChild = (node == parent.left)
        if (nodeIsLeftChild && isBlackOrNull(sibling.right)) {
            val tempNode =
                sibling.left ?: throw IllegalStateException("Left child of node with key ${sibling.key} can't be null")
            tempNode.color = Color.BLACK
            sibling.color = Color.RED
            rotateRight(sibling)
            mutableSibling =
                parent.right ?: throw IllegalStateException("Right child of node with key ${parent.key} can't be null")
        } else if (!nodeIsLeftChild && isBlackOrNull(sibling.left)) {
            val tempNode = sibling.right
                ?: throw IllegalStateException("Right child of node with key ${sibling.key} can't be null")
            tempNode.color = Color.BLACK
            sibling.color = Color.RED
            rotateLeft(sibling)
            mutableSibling =
                parent.left ?: throw IllegalStateException("Left child of node with key ${parent.key} can't be null")
        }

        mutableSibling.color = parent.color
        parent.color = Color.BLACK
        if (nodeIsLeftChild) {
            val tempNode = mutableSibling.right
                ?: throw IllegalStateException("Right child of node with key ${mutableSibling.key} can't be null")
            tempNode.color = Color.BLACK
            rotateLeft(parent)
        } else {
            val tempNode = mutableSibling.left
                ?: throw IllegalStateException("Left child of node with key ${parent.key} can't be null")
            tempNode.color = Color.BLACK
            rotateRight(parent)
        }
    }

    private fun getSibling(node: RBNode<K, V>): RBNode<K, V>? {
        val parent = node.parent
        return when (node) {
            parent?.left -> parent.right
            parent?.right -> parent.left
            else -> throw IllegalStateException("Node with key ${node.key} is not a child of its parent")
        }
    }

    private fun isBlackOrNull(node: RBNode<K, V>?): Boolean {
        return node == null || node.color == Color.BLACK
    }
}
