abstract class BinaryTree<KV: Comparable<KV>, Node_type: Node<KV, Node_type>> {
    private var root: Node_type? = null

    fun find(node: Node<KV, Node_type>): Boolean {
        TODO("Not yet implemented")
    }
    abstract fun add(node: Node<KV, Node_type>)
    abstract fun remove(node: Node<KV, Node_type>)
}