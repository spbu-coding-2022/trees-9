package trees.nodes

class BSNode<K: Comparable<K>, V>(key: K, value: V) : Node<K, V, BSNode<K, V>>(key, value)
