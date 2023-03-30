package trees

import BSTNode
import KVPairs

fun main() {
//    var tree = BinaryTree<Int, String, KVPairs<Int, String>, Node<KVPairs, Node>>()
    val newpair = KVPairs(56, "удачи")
    val secondpair = KVPairs(86, "уди")
    val fourhtpair = KVPairs(34, "уди")
    val thirspair = KVPairs(89, "ачи")
    val newNode = BSTNode(newpair)
    val secondNode = BSTNode(secondpair)
    var fourthNode = BSTNode(fourhtpair)
    val thirdNode = BSTNode(thirspair)
    val newTree: BinarySearchTree<Int, String, KVPairs<Int, String>, BSTNode<KVPairs<Int, String>>> = BinarySearchTree()
//    newNode.right = secondNode
//    secondNode.right = thirdNode
//    newTree.root = newNode
    newTree.add(newNode)
    newTree.add(secondNode)
    newTree.add(thirdNode)
    println(newTree.find(thirdNode))
    TODO("СДЕЛАТЬ ОБЪВЛЕНИЕ ДЕРЕВА КРАСИВЕЕ И ПРОЩЕ, ТАК ЖЕ ПОДУМАТЬ НАД КОНСТРУКТОРОМ В НОДЕ")
}

