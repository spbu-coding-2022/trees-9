package trees

import trees.nodes.RBNode

class RBTree<T : Comparable<T>, NODE_TYPE : RBNode<T, NODE_TYPE>> : AbstractBalanceTree<T, NODE_TYPE>() {
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