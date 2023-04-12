package trees

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.TestInstance
import trees.nodes.BSNode
import kotlin.random.Random

const val seed = 74

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BSTreeTest {

    private val newInt = Random(seed)
    private val tree = BSTree<Int, Int>()
    // create pairs with int types, each int less than 'until' border
    private val treePairs = Array(1000) { Pair(newInt.nextInt(10000), newInt.nextInt(10000)) }
    @BeforeAll
    // another way - make it @beforeEach and put away @TestInstance
    fun init() {
        for (i in 0 until 1000)
            tree.add(BSNode(treePairs[i].first, treePairs[i].second))
    }

    @Test
    fun `find existing node`() {
        assertNotNull(tree.find(treePairs[408].first))
    }

    @Test
    fun `find not existing node`() {
        assertNull(tree.find(10001))
    }

    @Test
    fun add() {
    }

    @Test
    fun remove() {
    }
}