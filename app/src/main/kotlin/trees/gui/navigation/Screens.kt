package trees.gui.navigation

sealed class Screen {
    object homeScreen : Screen()
    object BSTScreen : Screen()
    object AVLScreen : Screen()
    object RBScreen : Screen()
}
