package trees.dataBases.RBT

import org.neo4j.driver.*
import org.neo4j.driver.exceptions.SessionExpiredException
import org.neo4j.driver.exceptions.value.Uncoercible
import trees.nodes.RBNode
import java.io.Closeable
import java.io.IOException

class Neo4jRepository: Closeable {
    private var driver: Driver? = null

    fun exportRBTree(root: RBNode<Int, String>) {
        val session = driver?.session() ?: throw IOException("Driver is not open")
        session.executeWrite { tx ->
            cleanDataBase(tx)
            tx.run(genExportRBNodes(root))
        }
        session.close()
    }


    fun importRBTree(): RBNode<Int, String>? {
        val session = driver?.session() ?: throw IOException("Driver is not open")
        val res: RBNode<Int, String>? = session.executeRead { tx ->
            importRBNodes(tx)
        }
        session.close()
        return res
    }

    private fun cleanDataBase(tx: TransactionContext) {
        tx.run("MATCH (n: RBNode) DETACH DELETE n")
    }

    private fun genExportRBNodes(root: RBNode<Int, String>): String {
        val sb = StringBuilder()
        traverseExportRBNode(sb, root)
        return sb.toString()
    }

    private fun traverseExportRBNode(
        sb: StringBuilder,
        nodeView: RBNode<Int, String>,
    ) {
        sb.append(
            "CREATE (:RBNode {key : \"${nodeView.key}\", " +
                    "value: \"${nodeView.value}\""
        )
    }

    private fun importRBNodes(tx: TransactionContext): RBNode<Int, String>? {
        val nodeAndKeysRecords = tx.run(
            "MATCH (p: RBNode)" +
                    "RETURN p.key AS key, p.value AS value"
        )
        return parseRBNodes(nodeAndKeysRecords)
    }

    private class NodeAndKeys(
        val nv: RBNode<Int, String>,
    )

    private fun parseRBNodes(nodeAndKeysRecords: Result): RBNode<Int, String>? {
        val key2nk = mutableMapOf<Int, NodeAndKeys>()
        for (nkRecord in nodeAndKeysRecords) {
            try {
                val key = nkRecord["key"].asInt()
                val value = nkRecord["value"].asString()
                val nv = RBNode(key, value)
                key2nk[key] = NodeAndKeys(nv)
            } catch (ex: Uncoercible) {
                throw IOException("Invalid nodes label in the database", ex)
            }
        }
        val nks = key2nk.values.toTypedArray()
        if (nks.isEmpty()) { // if nodeAndKeysRecords was empty
            return null
        }
        if (key2nk.values.size != 1) {
            throw IOException("Found ${key2nk.values.size} nodes without parents in database, expected only 1 node")
        }
        val root = key2nk.values.first().nv
        return root
    }

    fun open(uri: String, username: String, password: String) {
        try {
            driver = GraphDatabase.driver(uri, AuthTokens.basic(username, password))
        } catch (ex: IllegalArgumentException) {
            throw IOException("Wrong URI", ex)
        } catch (ex: SessionExpiredException) {
            throw IOException("Session failed, try restarting the app", ex)
        }
    }

    override fun close() {
        driver?.close()
    }
}