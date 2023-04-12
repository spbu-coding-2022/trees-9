package trees

import org.checkerframework.checker.units.qual.K
import trees.abstract_trees.BalanceTree
import trees.nodes.RBNode


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

        balance(node)
    }
    override fun balance(node: RBNode<K, V>) {
        var parent = node.parent;

        // Case 1: Parent is null, we've reached the root, the end of the recursion
        if (parent == null) {
            // Uncomment the following line if you want to enforce black roots (rule 2):
            // node.color = BLACK;
            return;
        }

        // Parent is black --> nothing to do
        if (parent.color == RBNode.Color.BLACK) {
            return;
        }

        // From here on, parent is red
        var grandparent = parent.parent;

        // Case 2:
        // Not having a grandparent means that parent is the root. If we enforce black roots
        // (rule 2), grandparent will never be null, and the following if-then block can be
        // removed.
        if (grandparent == null) {
            // As this method is only called on red nodes (either
            // recursively on red grandparents), all we have to do is to recolor the root black.
            parent.color = RBNode.Color.BLACK;
            return;
        }

        // Get the uncle (maybe null/nil, in which case its color is BLACK)
        var uncle = getUncle(parent);

        // Case 3: Uncle is red -> recolor parent, grandparent and uncle
        if (uncle != null && uncle.color == RBNode.Color.RED) {
            parent.color = RBNode.Color.BLACK;
            grandparent.color = RBNode.Color.RED;
            uncle.color = RBNode.Color.BLACK;

            // Call recursively for grandparent, which is now red.
            // It might be root or have a red parent, in which case we need to fix more...
            balance(grandparent);
        }

        // Note on performance:
        // It would be faster to do the uncle color check within the following code. This way
        // we would avoid checking the grandparent-parent direction twice (once in getUncle()
        // and once in the following else-if). But for better understanding of the code,
        // I left the uncle color check as a separate step.

        // Parent is left child of grandparent
        else if (parent == grandparent.left) {
            // Case 4a: Uncle is black and node is left->right "inner child" of its grandparent
            if (node == parent.right) {
                rotateLeft(parent);

                // Let "parent" point to the new root node of the rotated sub-tree.
                // It will be recolored in the next step, which we're going to fall-through to.
                parent = node;
            }

            // Case 5a: Uncle is black and node is left->left "outer child" of its grandparent
            rotateRight(grandparent);

            // Recolor original parent and grandparent
            parent.color = RBNode.Color.BLACK;
            grandparent.color = RBNode.Color.RED;
        }

        // Parent is right child of grandparent
        else {
            // Case 4b: Uncle is black and node is right->left "inner child" of its grandparent
            if (node == parent.left) {
                rotateRight(parent);

                // Let "parent" point to the new root node of the rotated sub-tree.
                // It will be recolored in the next step, which we're going to fall-through to.
                parent = node;
            }

            // Case 5b: Uncle is black and node is right->right "outer child" of its grandparent
            rotateLeft(grandparent);

            // Recolor original parent and grandparent
            parent.color = RBNode.Color.BLACK;
            grandparent.color = RBNode.Color.RED;
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

    override fun remove(node: RBNode<K, V>) {
        TODO("Not yet implemented")
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

    fun deleteNode(insertNode: RBNode<K, V>) {
        var node = root

        // Find the node to be deleted
        while (node != null && node.key != insertNode.key) {
            // Traverse the tree to the left or right depending on the key
            node = if (insertNode.key < node.key) {
                node.left
            } else {
                node.right
            }
        }

        // Node not found?
        if (node == null) {
            return
        }

        // At this point, "node" is the node to be deleted

        // In this variable, we'll store the node at which we're going to start to fix the R-B
        // properties after deleting a node.
        val movedUpNode: RBNode<K, V>
        val deletedNodeColor: RBNode.Color

        // Node has zero or one child
        if (node.left == null || node.right == null) {
            movedUpNode = deleteNodeWithZeroOrOneChild(node)
            deletedNodeColor = node.color
        } else {
            // Find minimum node of right subtree ("inorder successor" of current node)
            val inOrderSuccessor: RBNode<K, V> = findMinimum(node.right)

            // Copy inorder successor's data to current node (keep its color!)
            node.key = inOrderSuccessor.key

            // Delete inorder successor just as we would delete a node with 0 or 1 child
            movedUpNode = deleteNodeWithZeroOrOneChild(inOrderSuccessor)
            deletedNodeColor = inOrderSuccessor.color
        }
        if (deletedNodeColor == RBNode.Color.BLACK) {
            fixRedBlackPropertiesAfterDelete(movedUpNode)

            // Remove the temporary NIL node
            if (movedUpNode.javaClass == NILNode()) {
                replaceParentsChild(movedUpNode.parent, movedUpNode, null)
            }
        }
    }
    private fun deleteNodeWithZeroOrOneChild(node: RBNode<K, V>): RBNode<K, V>? {
        // Node has ONLY a left child --> replace by its left child
        return if (node.left != null) {
            replaceParentsChild(node.parent, node, node.left)
            node.left // moved-up node
        } else if (node.right != null) {
            replaceParentsChild(node.parent, node, node.right)
            node.right // moved-up node
        } else {
            val newChild: RBNode<K, V>? = if (node.color == RBNode.Color.BLACK) NilNode() else null
            replaceParentsChild(node.parent, node, newChild)
            newChild
        }
    }
    private fun findMinimum(node: RBNode<K, V>): RBNode<K, V>? {
        var current: RBNode<K, V>? = node
        while (current?.left != null) {
            current = current.left
        }
        return current
    }

    private fun fixRedBlackPropertiesAfterDelete(node: RBNode<K, V>) {
        // Case 1: Examined node is root, end of recursion
        if (node == root) {
            // Uncomment the following line if you want to enforce black roots (rule 2):
            // node.color = BLACK;
            return
        }
        var sibling: RBNode<K, V> = getSibling(node)

        // Case 2: Red sibling
        if (sibling.color == RBNode.Color.RED) {
            handleRedSibling(node, sibling)
            sibling = getSibling(node) // Get new sibling for fall-through to cases 3-6
        }

        // Cases 3+4: Black sibling with two black children
        if (sibling.left!!.color == RBNode.Color.BLACK && sibling.right!!.color == RBNode.Color.BLACK) {
            sibling.color = RBNode.Color.RED

            // Case 3: Black sibling with two black children + red parent
            if (node.parent!!.color == RBNode.Color.RED) {
                node.parent!!.color = RBNode.Color.BLACK
            } else {
                fixRedBlackPropertiesAfterDelete(node.parent!!)
            }
        } else {
            handleBlackSiblingWithAtLeastOneRedChild(node, sibling)
        }
    }
    private fun handleRedSibling(node: RBNode<K, V>, sibling: RBNode<K, V>) {
        // Recolor...
        sibling.color = RBNode.Color.BLACK
        node.parent!!.color = RBNode.Color.RED

        // ... and rotate
        if (node == node.parent!!.left) {
            rotateLeft(node.parent!!)
        } else {
            rotateRight(node.parent!!)
        }
    }
    private fun handleBlackSiblingWithAtLeastOneRedChild(node: RBNode<K, V>, sibling: RBNode<K, V>) {
        var sibling: RBNode<K, V> = sibling
        val nodeIsLeftChild = node == node.parent.left

        // Case 5: Black sibling with at least one red child + "outer nephew" is black
        // --> Recolor sibling and its child, and rotate around sibling
        if (nodeIsLeftChild && isBlack(sibling.right)) {
            sibling.left.color = BLACK
            sibling.color = RED
            rotateRight(sibling)
            sibling = node.parent.right
        } else if (!nodeIsLeftChild && isBlack(sibling.left)) {
            sibling.right.color = BLACK
            sibling.color = RED
            rotateLeft(sibling)
            sibling = node.parent.left
        }

        // Fall-through to case 6...

        // Case 6: Black sibling with at least one red child + "outer nephew" is red
        // --> Recolor sibling + parent + sibling's child, and rotate around parent
        sibling.color = node.parent.color
        node.parent.color = BLACK
        if (nodeIsLeftChild) {
            sibling.right.color = BLACK
            rotateLeft(node.parent)
        } else {
            sibling.left.color = BLACK
            rotateRight(node.parent)
        }
    }

    private fun getSibling(node: Node): Node? {
        val parent: Node = node.parent
        return if (node === parent.left) {
            parent.right
        } else if (node === parent.right) {
            parent.left
        } else {
            throw java.lang.IllegalStateException("Parent is not a child of its grandparent")
        }
    }

    private fun isBlack(node: Node?): Boolean {
        return node == null || node.color === BLACK
    }


    private class NilNode private constructor() : Node(0) {
        init {
            this.color = BLACK
        }
    }

}
