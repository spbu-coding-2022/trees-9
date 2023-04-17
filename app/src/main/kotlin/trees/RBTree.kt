package trees

import trees.abstract_trees.BalanceTree
import trees.nodes.RBNode
import trees.nodes.RBNode.Color

class RBTree<K : Comparable<K>, V> : BalanceTree<K, V, RBNode<K, V>>() {
    override fun add(node: RBNode<K, V>) {
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

        balanceAfterInsert(node)
    }

    override fun remove(node: RBNode<K, V>) {
        var current = root
        while (current != null && current.key != node.key) {
            current = if (node.key < current.key) {
                current.left
            } else {
                current.right
            }
        }
        if (current == null) {
            throw IllegalStateException("There is no such node with key ${node.key} in tree")
        }

        val movedUpNode: RBNode<K, V>?
        val deletedNodeColor: Color

        // Node has zero or one child
        if (current.left == null || current.right == null) {
            movedUpNode = deleteNodeWithZeroOrOneChild(current)
            deletedNodeColor = current.color
        } else {
            // Find minimum node of right subtree ("inorder successor" of current node)
            val inOrderSuccessor: RBNode<K, V> = findMinimum(current.right!!)

            // Copy inorder successor's data to current node (keep its color!)
            current.key = inOrderSuccessor.key

            // Delete inorder successor just as we would delete a node with 0 or 1 child
            movedUpNode = deleteNodeWithZeroOrOneChild(inOrderSuccessor)!!
            deletedNodeColor = inOrderSuccessor.color
        }
        if (deletedNodeColor == Color.BLACK) {
            balanceAfterDelete(movedUpNode!!)

            // Remove the temporary NIL node
            if (movedUpNode.isNil) {
                replaceParentsChild(movedUpNode.parent, movedUpNode, null)
            }
        }
    }

    override fun balance(node: RBNode<K, V>) {
        TODO("Not yet implemented")
    }

    private fun balanceAfterInsert(node: RBNode<K, V>) {
        var parent = node.parent ?: return;
        if (parent.color == Color.BLACK) {
            return
        }

        val grandparent = parent.parent;
        if (grandparent == null) {
            parent.color = Color.BLACK;
            return
        }

        val uncle = getUncle(parent);
        if (uncle != null && uncle.color == Color.RED) {
            parent.color = Color.BLACK;
            grandparent.color = Color.RED;
            uncle.color = Color.BLACK;
            balanceAfterInsert(grandparent);
        } else if (parent == grandparent.left) {
            if (node == parent.right) {
                rotateLeft(parent);
                parent = node;
            }
            rotateRight(grandparent);
            parent.color = Color.BLACK;
            grandparent.color = Color.RED;
        } else {
            if (node == parent.left) {
                rotateRight(parent);
                parent = node;
            }
            rotateLeft(grandparent);
            parent.color = Color.BLACK;
            grandparent.color = Color.RED;
        }
    }

    private fun getUncle(parent: RBNode<K, V>): RBNode<K, V>? {
        val grandparent = parent.parent
        return if (grandparent?.left == parent) {
            grandparent.right
        } else if (grandparent?.right == parent) {
            grandparent.left
        } else {
            throw IllegalStateException("Parent is not a child of its grandparent")
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
            throw IllegalStateException("Node is not a child if its parent")
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


    private fun deleteNodeWithZeroOrOneChild(node: RBNode<K, V>): RBNode<K, V>? {
        return if (node.left != null) {
            replaceParentsChild(node.parent, node, node.left)
            node.left
        } else if (node.right != null) {
            replaceParentsChild(node.parent, node, node.right)
            node.right
        } else {
            var newChild: RBNode<K, V>? = node
            newChild!!.isNil = true
            if (node.color == Color.RED) {
                newChild = null
            }
            replaceParentsChild(node.parent, node, newChild)
            newChild
        }
    }

    private fun findMinimum(node: RBNode<K, V>): RBNode<K, V> {
        var current = node
        while (current.left != null) {
            current = current.left!!
        }
        return current
    }

    private fun balanceAfterDelete(node: RBNode<K, V>) {
        // Case 1: Examined node is root, end of recursion
        if (node == root) {
            // Uncomment the following line if you want to enforce black roots (rule 2):
            // node.color = BLACK;
            return
        }
        var sibling = getSibling(node)

        // Case 2: Red sibling
        if (sibling?.color == Color.RED) {
            handleRedSibling(node, sibling)
            sibling = getSibling(node)!! // Get new sibling for fall-through to cases 3-6
        }

        // Cases 3+4: Black sibling with two black children
        if (isBlack(sibling?.left) && isBlack(sibling?.right)) {
            sibling?.color = Color.RED

            // Case 3: Black sibling with two black children + red parent
            if (node.parent!!.color == Color.RED) {
                node.parent!!.color = Color.BLACK
            } else {
                balanceAfterDelete(node.parent!!)
            }
        } else {
            handleBlackSiblingWithAtLeastOneRedChild(node, sibling!!)
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
    private fun handleBlackSiblingWithAtLeastOneRedChild(node: RBNode<K, V>, sibling: RBNode<K, V>) {
        val nodeIsLeftChild = node == node.parent!!.left

        // Case 5: Black sibling with at least one red child + "outer nephew" is black
        // --> Recolor sibling and its child, and rotate around sibling
        if (nodeIsLeftChild && isBlack(sibling.right)) {
            sibling.left!!.color = Color.BLACK
            sibling.color = Color.RED
            rotateRight(sibling)
            sibling.parent!!.right =  node.parent!!.right
        } else if (!nodeIsLeftChild && isBlack(sibling.left)) {
            sibling.right!!.color = Color.BLACK
            sibling.color = Color.RED
            rotateLeft(sibling)
            sibling.parent!!.left = node.parent!!.left
        }

        // Fall-through to case 6...

        // Case 6: Black sibling with at least one red child + "outer nephew" is red
        // --> Recolor sibling + parent + sibling's child, and rotate around parent
        sibling.color = node.parent!!.color
        node.parent!!.color = Color.BLACK
        if (nodeIsLeftChild) {
            sibling.right!!.color = Color.BLACK
            rotateLeft(node.parent!!)
        } else {
            sibling.left!!.color = Color.BLACK
            rotateRight(node.parent!!)
        }
    }

    private fun getSibling(node: RBNode<K, V>): RBNode<K, V>? {
        val parent = node.parent
        return if (node == parent?.left) {
            parent.right
        } else if (node == parent?.right) {
            parent.left
        } else {
            throw IllegalStateException("Parent is not a child of its grandparent")
        }
    }

    private fun isBlack(node: RBNode<K, V>?): Boolean {
        return node == null || node.color == Color.BLACK
    }
}
