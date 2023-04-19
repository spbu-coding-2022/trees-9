package treesValidChecker

import trees.AVLTree
import trees.BSTree
import trees.RBTree
import trees.nodes.AVLNode
import trees.nodes.BSNode
import trees.nodes.RBNode
import kotlin.math.abs

val BStree = BSTree<Int, Int>()

fun isBstOk(node: BSNode<Int, Int>? = BStree.root): Boolean {
    if (node == null) {
        return true
    }
    if (node.left != null && node.left!!.key > node.key) {
        return false
    }
    if (node.right != null && node.right!!.key < node.key) {
        return false
    }
    return !(!isBstOk(node.right) || !isBstOk(node.left))
}

val AVLtree = AVLTree<Int, Int>()

fun isAVLOk(node: AVLNode<Int, Int>? = AVLtree.root): Boolean {
    if (node == null) {
        return true
    }
    val lh = AVLtree.getHeight(node.left)
    val rh = AVLtree.getHeight(node.right)
    return abs(lh - rh) <= 1 && isAVLOk(node.left) && isAVLOk(node.right)
}

val RBtree = RBTree<Int, Int>()

fun isRbOk(node: RBNode<Int, Int>? = RBtree.root): Boolean {
    if (node == null) {
        return true
    }
    val blackLeft = numOfBlack(node.left)
    val blackRight = numOfBlack(node.right)
    return !(blackLeft != blackRight || blackLeft == -1 || !noTwoRedInARow())
}

fun numOfBlack(node: RBNode<Int, Int>? = RBtree.root): Int {
    if (node == null)
        return 0
    val blackLeft = numOfBlack(node.left)
    val blackRight = numOfBlack(node.right)
    if (blackRight != blackLeft || blackLeft == -1) {
        return -1
    }
    if (node.color == RBNode.Color.BLACK) {
        return 1 + blackLeft
    }
    return blackLeft
}

fun noTwoRedInARow(node: RBNode<Int, Int>? = RBtree.root, row: Int = 0): Boolean {
    if (node == null)
        return true
    if (node.color == RBNode.Color.BLACK) {
        return (noTwoRedInARow(node.left) && noTwoRedInARow(node.right))
    }
    if (row == 1)
        return false
    return (noTwoRedInARow(node.left, 1) && noTwoRedInARow(node.right, 1))
}