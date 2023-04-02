abstract class Node<T: Comparable<T>, NODE_TYPE: Node<T, NODE_TYPE>> {
    abstract var keyValue: T
    var left: NODE_TYPE? = null
    var right: NODE_TYPE? = null
}