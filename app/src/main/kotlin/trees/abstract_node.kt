package trees

class Node<Key : Comparable<Key>, Value>(
    var key: Key,
    var value: Value,
    var left: Node<Key, Value>? = null,
    var right: Node<Key, Value>? = null

) : Comparable<Key> {
    override fun compareTo(other: Key): Int {
        return key.compareTo(other)
    }

    fun equalKey(other: Key): Boolean {
        return this.compareTo(other) == 0
    }
}