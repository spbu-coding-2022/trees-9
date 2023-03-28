package trees

abstract class Tree<Key : Comparable<Key>, Value> (
    protected var rootNode: Node<Key, Value>? = null
) {
    abstract fun addNode()

    abstract fun removeNode()
    fun find(value: Key): Node<Key, Value>? {
        return rootNode?.let { trueFind(value, it) }
    }
    private fun trueFind(value: Key, node: Node<Key, Value>): Node<Key, Value>? = when {
        (node > value) -> node.left?.let { trueFind(value, it) }
        (node < value) -> node.right?.let { trueFind(value, it) }
        else -> node
    }
}