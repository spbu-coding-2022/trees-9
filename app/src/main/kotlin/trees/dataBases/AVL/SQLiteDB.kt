package main.kotlin.trees.dataBases.AVL

import mu.KotlinLogging
import java.io.Closeable
import java.sql.*
import trees.nodes.AVLNode
import trees.AVLTree

private val logger = KotlinLogging.logger { }
private const val DB_DRIVER = "jdbc:sqlite"

class SQLiteDB(private val path: String) : Closeable {
    private val connection = DriverManager.getConnection("$DB_DRIVER:$path")
        ?: throw SQLException("Cannot connect to database")
    private val addNodeStatement by lazy { connection.prepareStatement("INSERT INTO nodes(key, value) VALUES (?, ?);") }

    init {
        logger.info { "Connected to database: $path" }
    }

    fun open() {
        connection.createStatement().also { statement ->
            try {
                statement.execute("CREATE TABLE IF NOT EXISTS nodes(key INTEGER NOT NULL PRIMARY KEY, value text);")
                logger.info { "Tables created or already exists" }
            } catch (problem: Exception) {
                logger.error(problem) { "Cannot create table in database" }
            } finally {
                statement.close()
            }
        }
    }

    fun addNode(node: AVLNode<Int, String>) {
        try {
            addNodeStatement.setInt(1, node.key)
            addNodeStatement.setString(2, node.value)
            addNodeStatement.execute()
        } catch (problem: Exception) {
            logger.error(problem) { "Cannot add node with key ${node.key}" }
        }
    }

    fun writeAllNodesToDB(node: AVLNode<Int, String>?, tree: AVLTree<Int, String>) {
        val stack = mutableListOf(node?.key)
        while (stack.isNotEmpty()) {
            val current = stack.removeLast()?.let { tree.find(it) }
            if (current?.left != null)
                current.left?.key.apply(stack::add)
            if (current?.right != null)
                current.right?.key.apply(stack::add)
            current?.let { addNode((it)) }
        }
    }

    fun selectNodes(): MutableList<AVLNode<Int, String>> {
        val nodesList = mutableListOf<AVLNode<Int, String>>()
        try {
            val query = "SELECT key, value FROM nodes"
            val rs = connection.createStatement().executeQuery(query)
            while (rs.next()) {
                val key = rs.getInt("key")
                val value = rs.getString("value")
                var node = AVLNode(key,value)
                nodesList.add(node)
            }
        } catch (problem: Exception) {
            logger.error(problem) { "Failed to select" }
        }
        return nodesList
    }

    fun delete() {
        connection.createStatement().also { statement ->
            try {
                statement.execute("DROP TABLE IF EXISTS nodes")
                logger.info { "Table deleted" }
            } catch (problem: Exception) {
                println(problem)
                logger.error(problem) { "Cannot delete table in database" }
            }  finally {
                statement.close()
            }
        }
    }

    override fun close() {
        addNodeStatement.close()
        connection.close()
        logger.info { "Connection closed" }
    }

}
