package trees

import trees.nodes.AVLNode

class AVLTree<T : Comparable<T>, NODE_TYPE : AVLNode<T, NODE_TYPE>> : AbstractBalanceTree<T, NODE_TYPE>() {
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