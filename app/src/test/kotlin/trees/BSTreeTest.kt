package trees

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.Assertions.*
import treesValidChecker.isBstOk

class BSTreeTest {
    private val tree = BSTree<Int, Int>()

    @Test
    fun `find existing node`() {
        for (i in (0..10000).shuffled()) {
            tree.add(i, 0)
        }
        assertNotNull(tree.find(408))
    }

    @Test
    fun `find not existing node`() {
        for (i in (0..10000).shuffled()) {
            tree.add(i, 0)
        }
        assertNull(tree.find(10001))
    }

    @Test
    fun `add 10000 elements`() {
        for (i in (0..10000).shuffled()) {
            tree.add(i, 0)
            assertTrue(isBstOk())
        }
    }

    @Test
    fun `remove 10000 elements`() {
        for (i in (0..10000).shuffled()) {
            tree.add(i, 0)
        }
        for (i in (0..10000).shuffled()) {
            tree.remove(i)
            assertTrue(isBstOk())
        }
    }
}