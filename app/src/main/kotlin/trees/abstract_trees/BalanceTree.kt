package trees.abstract_trees

import trees.nodes.Node

abstract class BalanceTree<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>> : BinaryTree<K, V, NODE_TYPE>() {
    protected abstract fun balance(node: NODE_TYPE, afterRemove: Boolean = false): NODE_TYPE
}
