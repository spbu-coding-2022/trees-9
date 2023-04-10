package trees.nodes
class RBNode<K: Comparable<K>, V>(key: K, value: V) : Node<K, V, RBNode<K, V>>(key, value) {
    var color = Color.RED
    enum class Color {
        RED,
        BLACK
    }
}
