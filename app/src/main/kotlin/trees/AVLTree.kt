package trees

import trees.abstract_trees.BalanceTree
import trees.nodes.AVLNode

class AVLTree<K: Comparable<K>, V> : BalanceTree<K, V, AVLNode<K, V>>() {
    override fun balance(node: AVLNode<K, V>) {
        TODO("Not yet implemented")
    }

    override fun add(node: AVLNode<K, V>) {
        TODO("Not yet implemented")
    }

    override fun remove(node: AVLNode<K, V>) {
        TODO("Not yet implemented")
    }
}
