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

        if (parent!!.key < node.key) {
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

        if (current.left == null || current.right == null) {
            movedUpNode = removeNodeWithZeroOrOneChild(current)
            deletedNodeColor = current.color
        } else {
            val inOrderSuccessor: RBNode<K, V> = getMinNode(current.right!!)
            current.key = inOrderSuccessor.key
            deletedNodeColor = inOrderSuccessor.color
            movedUpNode = removeNodeWithZeroOrOneChild(inOrderSuccessor)
        }

        if (deletedNodeColor == Color.BLACK) {
            balance(movedUpNode!!, true)
            if (movedUpNode.isTemp) {
                replaceParentsChild(movedUpNode.parent, movedUpNode, null)
            }
        }
    }

    override fun balance(node: RBNode<K, V>, afterRemove: Boolean?): RBNode<K, V>? {
        if (afterRemove!!) {
            balanceAfterRemove(node)
        } else {
            balanceAfterAdd(node)
        }
        return null
    }

    private fun balanceAfterAdd(node: RBNode<K, V>) {
        var parent = node.parent ?: return
        if (parent.color == Color.BLACK) {
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

        var sibling = getSibling(node)
        if (sibling?.color == Color.RED) {
            handleRedSibling(node, sibling)
            sibling = getSibling(node)
        }

        if (isBlackOrNull(sibling?.left) && isBlackOrNull(sibling?.right)) {
            sibling?.color = Color.RED
            if (node.parent!!.color == Color.RED) {
                node.parent!!.color = Color.BLACK
            } else {
                balanceAfterRemove(node.parent!!)
            }
        } else {
            handleBlackRedSibling(node, sibling!!)
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
        val parent: RBNode<K, V>? = node.parent
        val leftChild: RBNode<K, V>? = node.left
        node.left = leftChild?.right

        if (leftChild?.right != null) {
            leftChild.right!!.parent = node
        }

        leftChild!!.right = node
        node.parent = leftChild

        replaceParentsChild(parent, node, leftChild)
    }

    private fun rotateLeft(node: RBNode<K, V>) {
        val parent: RBNode<K, V>? = node.parent
        val rightChild: RBNode<K, V>? = node.right
        node.right = rightChild?.left

        if (rightChild?.left != null) {
            rightChild.left!!.parent = node
        }

        rightChild!!.left = node
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
            var newChild: RBNode<K, V>? = node
            newChild!!.isTemp = true
            if (node.color == Color.RED) {
                newChild = null
            }
            replaceParentsChild(node.parent, node, newChild)
            newChild
        }
    }

    private fun handleRedSibling(node: RBNode<K, V>, sibling: RBNode<K, V>) {
        sibling.color = Color.BLACK
        node.parent!!.color = Color.RED
        if (node == node.parent!!.left) {
            rotateLeft(node.parent!!)
        } else {
            rotateRight(node.parent!!)
        }
    }
    private fun handleBlackRedSibling(node: RBNode<K, V>, sibling: RBNode<K, V>) {
        var mutableSibling: RBNode<K, V> = sibling
        val nodeIsLeftChild = (node == node.parent!!.left)
        if (nodeIsLeftChild && isBlackOrNull(sibling.right)) {

            sibling.left!!.color = Color.BLACK
            sibling.color = Color.RED
            rotateRight(sibling)
            mutableSibling = node.parent!!.right!!
        } else if (!nodeIsLeftChild && isBlackOrNull(sibling.left)) {
            sibling.right!!.color = Color.BLACK
            sibling.color = Color.RED
            rotateLeft(sibling)
            mutableSibling = node.parent!!.left!!
        }

        mutableSibling.color = node.parent!!.color
        node.parent!!.color = Color.BLACK
        if (nodeIsLeftChild) {
            mutableSibling.right!!.color = Color.BLACK
            rotateLeft(node.parent!!)
        } else {
            mutableSibling.left!!.color = Color.BLACK
            rotateRight(node.parent!!)
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
