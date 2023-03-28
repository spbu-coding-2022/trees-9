package trees

class BSTree<Key : Comparable<Key>, Value>(key: Key, value: Value) : Tree<Key, Value>() {
    init {
        insert(key, value)
    }
    private fun insert(key: Key, value: Value) {
        if (rootNode == null)
            rootNode = Node(key, value)
    }
    override fun addNode() {
        TODO("Not yet implemented")
    }

    override fun removeNode() {
        TODO("Not yet implemented")
    }
}