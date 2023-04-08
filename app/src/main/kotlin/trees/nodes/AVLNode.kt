package trees.nodes

class AVLNode<T : Comparable<T>>(override var keyValue: T) : Node<T, AVLNode<T>>() {
    var height: Int? = null
}