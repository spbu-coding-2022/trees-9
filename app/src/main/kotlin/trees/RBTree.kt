package trees

import trees.abstract_trees.BalanceTree
import trees.nodes.RBNode

class RBTree<K : Comparable<K>, V> : BalanceTree<K, V, RBNode<K, V>>() {
    override fun add(node: RBNode<K, V>) {
        TODO("Not yet implemented")
    }

    override fun remove(key: K) {
        TODO("Not yet implemented")
    }

    override fun balance(node: RBNode<K, V>): RBNode<K, V>? {
        TODO("Not yet implemented")
    }
}
