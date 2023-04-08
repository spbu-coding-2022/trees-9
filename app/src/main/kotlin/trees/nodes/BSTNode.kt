package trees.nodes

class BSTNode<T : Comparable<T>>(override var keyValue: T) : Node<T, BSTNode<T>>()