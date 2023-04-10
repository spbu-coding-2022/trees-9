package trees

import trees.nodes.BSNode

fun main() {
    val t = BSTree<Int, String>()
    t.add(BSNode(5, "a"))
    t.add(BSNode(7, "b"))
    t.add(BSNode(3, "c"))
    t.add(BSNode(4, "d"))
    t.add(BSNode(2, "e"))
    t.print()
}

