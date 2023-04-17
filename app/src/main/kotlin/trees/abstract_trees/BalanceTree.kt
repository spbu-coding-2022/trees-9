package trees.abstract_trees

import trees.nodes.Node

abstract class BalanceTree<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>> : BinaryTree<K, V, NODE_TYPE>() {
    abstract fun balance(node: NODE_TYPE): NODE_TYPE?
}
