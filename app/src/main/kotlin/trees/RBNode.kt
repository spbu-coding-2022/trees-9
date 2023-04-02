class RBNode<T: Comparable<T>, NODE_TYPE: RBNode<T, NODE_TYPE>>(override var keyValue: T): Node<T, NODE_TYPE>(){

    var color = Color.RED

    enum class Color {
        RED,
        BLACK
    }
}