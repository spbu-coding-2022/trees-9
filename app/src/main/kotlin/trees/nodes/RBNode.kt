package trees.nodes

class RBNode<K : Comparable<K>, V>(key: K, value: V) : Node<K, V, RBNode<K, V>>(key, value) {
    var parent: RBNode<K, V>? = null
    var color: Color = Color.RED

    enum class Color {
        RED,
        BLACK
    }
    
    var isTemp: Boolean = false
}
