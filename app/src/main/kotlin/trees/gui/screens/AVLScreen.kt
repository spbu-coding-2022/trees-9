package trees.gui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import trees.BSTree
import trees.dataBases.BST.insertAllNodesToTree
import trees.dataBases.BST.removeFile
import trees.dataBases.BST.writeAllNodesToFile
import trees.gui.printNode
import trees.nodes.BSNode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalMapOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AVLScreen(toMenu: () -> Unit) {
    Box() {
        Column(
            Modifier.padding(10.dp, 10.dp, 0.dp, 0.dp).background(Color.Unspecified).shadow(1.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start,
        ) {
            Column(
                Modifier.width(200.dp).height(260.dp).offset(0.dp, 0.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                var key by remember { mutableStateOf("") }
                TextField(
                    modifier = Modifier.width(180.dp).padding(vertical = 15.dp).clip(RoundedCornerShape(25.dp)),
                    value = key,
                    onValueChange = { key = it },
                    label = { Text("Enter key") }
                )
                var value by remember { mutableStateOf("") }
                TextField(
                    modifier = Modifier.width(180.dp).padding(bottom = 15.dp).clip(RoundedCornerShape(25.dp)),
                    value = value,
                    onValueChange = { value = it },
                    label = { Text("Enter value") }
                )
                Button(
                    onClick = {
                    },
                ) {
                    Text(
                        text = "Insert node",
                        modifier = Modifier.width(110.dp),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Button(
                    onClick = {
                    }) {
                    Text(
                        text = "Remove node",
                        modifier = Modifier.width(110.dp),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Button(
                    onClick = {
                    }) {
                    Text(
                        text = "Find node",
                        modifier = Modifier.width(110.dp),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Column(
                Modifier.width(200.dp).height(180.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Button(
                    onClick = {
                    }) {
                    Text(
                        text = "Save tree",
                        modifier = Modifier.width(110.dp),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Button(
                    onClick = {
                    }) {
                    Text(
                        text = "Clear DB file",
                        modifier = Modifier.width(110.dp),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
                Button(
                    onClick = {
                        toMenu()
                    }) {
                    Text(
                        text = "Back to menu",
                        modifier = Modifier.width(110.dp),
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}