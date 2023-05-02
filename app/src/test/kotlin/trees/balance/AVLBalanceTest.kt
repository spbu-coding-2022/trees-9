package trees.balance

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import trees.AVLTree

class AVLBalanceTest {

    private val tree = AVLTree<Int, String>()

    @Test
    fun `test method add in AVLTree (1)`() {
        tree.add(10, "hello")
        tree.add(8, "hello")
        tree.add(6, "hello")
        /*

                    10
                  /                          8
                8       --------->        /    \
              /                         6        10
            6

         */

        Assertions.assertEquals(8, tree.root?.key)
        Assertions.assertEquals(6, tree.root?.left?.key)
        Assertions.assertEquals(10, tree.root?.right?.key)
    }

    @Test
    fun `test method add in AVLTree (2)`() {
        tree.add(8, "hello")
        tree.add(6, "hello")
        tree.add(10, "hello")
        tree.add(11, "hello")
        tree.add(12, "hello")
        /*
                8
              /   \                                  8
            6      10                              /   \
                     \         --------->         6     11
                      11                               /  \
                       \                             10    12
                        12
         */

        Assertions.assertEquals(8, tree.root?.key)
        Assertions.assertEquals(6, tree.root?.left?.key)
        Assertions.assertEquals(11, tree.root?.right?.key)
        Assertions.assertEquals(10, tree.root?.right?.left?.key)
        Assertions.assertEquals(12, tree.root?.right?.right?.key)
    }

    @Test
    fun `test method remove in AVLTree(remove without balance)`() {
        tree.add(8, "hello")
        tree.add(6, "hello")
        tree.add(10, "hello")
        tree.add(11, "hello")
        tree.add(12, "hello")

        tree.remove(12)
        /*
                8                                     8
              /   \                                  / \
             6     11           --------->          6   11
                  /  \                                 /
                10    12                              10

         */

        Assertions.assertEquals(8, tree.root?.key)
        Assertions.assertEquals(6, tree.root?.left?.key)
        Assertions.assertEquals(11, tree.root?.right?.key)
        Assertions.assertEquals(10, tree.root?.right?.left?.key)
        Assertions.assertEquals(null, tree.root?.right?.right?.key)
    }

    @Test
    fun `test method remove in AVLTree(remove with balance)`() {
        tree.add(8, "hello")
        tree.add(6, "hello")
        tree.add(10, "hello")
        tree.add(11, "hello")
        tree.add(12, "hello")

        tree.remove(6)
        /*
                8                                     11
              /   \                                  /  \
             6     11           --------->          8   12
                  /  \                               \
                10    12                              10

         */

        Assertions.assertEquals(11, tree.root?.key)
        Assertions.assertEquals(8, tree.root?.left?.key)
        Assertions.assertEquals(12, tree.root?.right?.key)
        Assertions.assertEquals(10, tree.root?.left?.right?.key)
    }
}