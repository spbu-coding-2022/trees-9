package trees

import KVPairs
import Node

class BinarySearchTree<K: Comparable<K>, V, KV: KVPairs<K, V>, NODE_TYPE: Node<KV, NODE_TYPE>>: BinaryTree<K, V, KV, NODE_TYPE>() {
    override fun add(start_node: NODE_TYPE?, node: NODE_TYPE): NODE_TYPE {
        if (root === start_node && start_node == null) {
            root = node
            return node
        }
        if (start_node == null) {
            return node
        }

        if (start_node.keyValue < node.keyValue) {
            start_node.right = add(start_node.right, node)
        } else {
            start_node.left = add(start_node.left, node)
        }
        return start_node
    }
}