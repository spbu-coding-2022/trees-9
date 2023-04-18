package trees.dataBases.RBT

import org.neo4j.driver.AuthTokens
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.neo4j.driver.TransactionContext
import org.neo4j.driver.exceptions.SessionExpiredException
import trees.nodes.RBNode
import java.io.Closeable
import java.io.IOException

class Neo4jRepository: Closeable {
    private var driver: Driver? = null

    private fun cleanDataBase(tx: TransactionContext) {
        TODO("Implement cleaning of DB")
    }

    fun exportRBTree(root: RBNode<Int, String>) {   // when we have treeView, fun will be rewritten
        val session = driver?.session() ?: throw IOException("Driver is not open")
        session.executeWrite { tx ->
            TODO("Implement write to DB")
        }
        session.close()
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