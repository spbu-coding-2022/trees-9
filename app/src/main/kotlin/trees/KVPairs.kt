class KVPairs<K : Comparable<K>, V>(val key: K, val value: V?) : Comparable<KVPairs<K, V>> {
    override fun compareTo(anotherPair: KVPairs<K, V>): Int {
        return key.compareTo(anotherPair.key)
    }
}