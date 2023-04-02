package trees

import trees.nodes.AVLNode

class AVLTree<K : Comparable<K>, V, KV : KVPairs<K, V>, NODE_TYPE : Node<KV, NODE_TYPE>> :
    AbstractBalanceTree<K, V, KV, NODE_TYPE>() {
    override fun balance() {
        TODO("Not yet implemented")
    }

    override fun add(node: NODE_TYPE) {
        TODO("Not yet implemented")
    }

    override fun delete(node: NODE_TYPE) {
        TODO("Not yet implemented")
    }
}