abstract class Node<K: Comparable<K>, V>(var keyValue: KVPairs<K, V>) : Comparable<Node<K, V>> {
    var left: Node<K, V>? = null
    var right: Node<K, V>? = null
}