package trees

import KVPairs


abstract class AbstractBalanceTree<K : Comparable<K>, V, KV : KVPairs<K, V>, NODE_TYPE : Node<KV, NODE_TYPE>> :
    BinaryTree<K, V, KV, NODE_TYPE>() {
    abstract fun balance();

    }