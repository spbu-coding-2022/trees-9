package treesValidChecker

import trees.BSTree
import trees.nodes.BSNode

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