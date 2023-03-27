package trees

abstract class Tree<Key : Comparable<Key>, Value> {
    private var rootNode: Node<Key, Value>? = null

    abstract fun addNode()

    abstract fun removeNode()

    fun find(value: Int) {
        TODO("not implemented yet")
//        if (this == null)
//            return null
//        if this.Key
    }
}