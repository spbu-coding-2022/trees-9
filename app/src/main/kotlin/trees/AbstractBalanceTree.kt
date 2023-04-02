package trees

import trees.nodes.Node


abstract class AbstractBalanceTree<T : Comparable<T>, NODE_TYPE : Node<T, NODE_TYPE>> : BinaryTree<T, NODE_TYPE>() {
    abstract fun balance()
}