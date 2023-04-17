package treesValidChecker

import trees.AVLTree
import trees.BSTree
import trees.nodes.AVLNode
import trees.nodes.BSNode
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
    return abs(lh-rh) <= 1 && isAVLOk(node.left) && isAVLOk(node.right)
}