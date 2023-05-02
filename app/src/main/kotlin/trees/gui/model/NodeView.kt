package trees.gui.model

import trees.nodes.Node

class NodeView<NT : Node<Int, String?, NT>>(parNode: NT) {
    var node = parNode
    val x = 0.0
    val y = 0.0
    var left: NodeView<NT>? = null
    var right: NodeView<NT>? = null

    init {
        node.left?.let {
            left = NodeView(it)
        }
        node.right?.let {
            right = NodeView(it)
        }
    }
}
