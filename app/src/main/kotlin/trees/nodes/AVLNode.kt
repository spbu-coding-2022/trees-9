package trees.nodes

class AVLNode<T: Comparable<T>, NODE_TYPE: AVLNode<T, NODE_TYPE>>(override var keyValue: T): Node<T, NODE_TYPE>(){
    var height: Int? = null
}