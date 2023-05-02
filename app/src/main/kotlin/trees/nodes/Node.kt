package trees.nodes

abstract class Node<K : Comparable<K>, V, NODE_TYPE : Node<K, V, NODE_TYPE>>(var key: K, var value: V) {
    var left: NODE_TYPE? = null
    var right: NODE_TYPE? = null
}
