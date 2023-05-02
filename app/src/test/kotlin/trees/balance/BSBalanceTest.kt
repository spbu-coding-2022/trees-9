package trees.balance

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import trees.BSTree

class BSBalanceTest {
    private val tree = BSTree<Int, String>()

    @Test
    fun `test method add in BSTree (1)`() {
        tree.add(10, "hello")
        tree.add(5, "hello")
        tree.add(7, "hello")
        tree.add(13, "hello")
        tree.add(11, "hello")
        tree.add(14, "hello")
        tree.add(12, "hello")
        tree.add(4, "hello")

        /*
                        10
                    /        \
                  5            13
                /  \         /   \
              6     7      11     14
                             \
                              12
         */

        assertEquals(10, tree.root?.key)
        assertEquals(5, tree.root?.left?.key)
        assertEquals(13, tree.root?.right?.key)
        assertEquals(4, tree.root?.left?.left?.key)
        assertEquals(7, tree.root?.left?.right?.key)
        assertEquals(14, tree.root?.right?.right?.key)
        assertEquals(11, tree.root?.right?.left?.key)
        assertEquals(12, tree.root?.right?.left?.right?.key)
    }

    @Test
    fun `test method add in BSTree (2)`() {
        tree.add(10, "hello")
        tree.add(9, "hello")
        tree.add(8, "hello")
        tree.add(7, "hello")

        /*
                    10
                   /
                  9
                 /
                8
               /
              7
         */

        assertEquals(10, tree.root?.key)
        assertEquals(9, tree.root?.left?.key)
        assertEquals(null, tree.root?.right?.key)
        assertEquals(8, tree.root?.left?.left?.key)
        assertEquals(7, tree.root?.left?.left?.left?.key)
    }

    @Test
    fun `test method remove in BSTree(remove leaf)`() {
        tree.add(10, "hello")
        tree.add(5, "hello")
        tree.add(7, "hello")

        tree.remove(7)
        /*
                    10
                   /                             10
                  5         -------->           /
                   \                           5
                     7
         */

        assertEquals(10, tree.root?.key)
        assertEquals(5, tree.root?.left?.key)
        assertEquals(null, tree.root?.left?.right?.key)
        assertEquals(null, tree.root?.left?.left?.key)
    }

    @Test
    fun `test method remove in BSTree(remove node with one child)`() {
        tree.add(10, "hello")
        tree.add(5, "hello")
        tree.add(7, "hello")

        tree.remove(5)
        /*
            10
           /                             10
          5         -------->           /
           \                           7
             7
        */

        assertEquals(10, tree.root?.key)
        assertEquals(7, tree.root?.left?.key)
        assertEquals(null, tree.root?.right?.key)
        assertEquals(null, tree.root?.left?.right?.key)
        assertEquals(null, tree.root?.left?.left?.key)
    }

    @Test
    fun `test method remove in BSTree(remove node with two child)`() {
        tree.add(10, "hello")
        tree.add(5, "hello")
        tree.add(12, "hello")

        tree.remove(10)
        /*
            10                           12
           /  \       -------->         /
          5    12                      5
        */

        assertEquals(12, tree.root?.key)
        assertEquals(5, tree.root?.left?.key)
        assertEquals(null, tree.root?.right?.key)
    }
}