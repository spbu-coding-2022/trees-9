package trees.nodes

class BSTNode<T : Comparable<T>, NODE_TYPE : BSTNode<T, NODE_TYPE>>(override var keyValue: T) : Node<T, NODE_TYPE>()