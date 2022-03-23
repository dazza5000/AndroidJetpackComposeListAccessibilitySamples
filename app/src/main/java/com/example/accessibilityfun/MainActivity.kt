package com.example.accessibilityfun

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.accessibilityfun.ui.theme.AccessibilityFunTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AccessibilityFunTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    androidx.compose.foundation.layout.Column {
                        Text("Row",
                            style = TextStyle(fontSize = 36.sp)
                        )
                        Spacer(modifier = Modifier.padding(horizontal = 24.dp))
                        TestRow()
                        Spacer(modifier = Modifier.padding(horizontal = 24.dp))
                        Text("Column",
                            style = TextStyle(fontSize = 36.sp)
                        )
                        TestColumn()
                    }
                }
            }
        }
    }
}

val listItems = listOf("Apple", "Orange", "Banana", "Grape", "Tomato")

@Composable
fun TestRow() {
    LazyRow {
        items(listItems.size, itemContent = { index ->
            Text(
                text = listItems[index],
                modifier = Modifier.padding(12.dp),
                style = TextStyle(fontSize = 24.sp)
            )
        })
    }
}

@Composable
fun TestColumn() {
    val columnItems = listItems.toMutableList().plus(listItems).plus(listItems)
    LazyColumn {
        items(columnItems.size, itemContent = { index ->
            Text(
                text = columnItems[index],
                modifier = Modifier.padding(12.dp),
                style = TextStyle(fontSize = 24.sp)
            )
        })
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AccessibilityFunTheme {
        TestRow()
    }
}