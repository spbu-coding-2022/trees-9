abstract class Node<T : Comparable<T>, NODE_TYPE : Node<T, NODE_TYPE>> {
    abstract var keyValue: T
    var left: NODE_TYPE? = null
    var right: NODE_TYPE? = null
}

class BSTNode<T : Comparable<T>>(override var keyValue: T) : Node<T, BSTNode<T>>()