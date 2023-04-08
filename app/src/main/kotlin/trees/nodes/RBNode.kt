package trees.nodes
class RBNode<T : Comparable<T>>(override var keyValue: T) : Node<T, RBNode<T>>() {

    var color = Color.RED

    enum class Color {
        RED,
        BLACK
    }
}