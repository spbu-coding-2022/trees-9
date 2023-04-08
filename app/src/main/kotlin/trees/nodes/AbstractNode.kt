package trees.nodes
abstract class Node<T: Comparable<T>, NODE_TYPE: Node<T, NODE_TYPE>> {
    abstract var keyValue: T
    var left: NODE_TYPE? = null
    var right: NODE_TYPE? = null
}

class KVPairs<K : Comparable<K>, V>(val key: K, val value: V?) : Comparable<KVPairs<K, V>> {
    override fun compareTo(other: KVPairs<K, V>): Int {
        return key.compareTo(other.key)
    }
}