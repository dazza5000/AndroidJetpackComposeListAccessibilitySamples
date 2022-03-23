package com.example.accessibilityfun

import android.os.Bundle
import android.text.SpannableString
import android.text.style.BulletSpan
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.accessibilityfun.ui.theme.AccessibilityFunTheme
import com.google.accompanist.web.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var showMenu by remember { mutableStateOf(false) }
            var listType by remember { mutableStateOf(List.ROW) }

            AccessibilityFunTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = {
                                Text(text = stringResource(R.string.app_name))
                            },
                            actions = {
                                IconButton(onClick = { showMenu = !showMenu }) {
                                    Icon(
                                        imageVector = Icons.Filled.MoreVert,
                                        contentDescription = "More",
                                    )
                                }
                                DropdownMenu(
                                    expanded = showMenu,
                                    onDismissRequest = { showMenu = false }
                                ) {
                                    DropdownMenuItem(onClick = {
                                        listType = List.ROW
                                    }) {
                                        Text("Row")
                                    }
                                    DropdownMenuItem(onClick = {
                                        listType = List.COLUMN
                                    }) {
                                        Text("Column")
                                    }
                                    DropdownMenuItem(onClick = {
                                        listType = List.SPANNABLE
                                    }) {
                                        Text(List.SPANNABLE.toString())
                                    }
                                    DropdownMenuItem(onClick = {
                                        listType = List.TEXT_LIST
                                    }) {
                                        Text(List.TEXT_LIST.toString())
                                    }
                                    DropdownMenuItem(onClick = {
                                        listType = List.WEBVIEW
                                    }) {
                                        Text(List.WEBVIEW.toString())
                                    }
                                }
                            }
                        )
                    },
                    content = {

                        Surface(
                            modifier = Modifier.fillMaxSize(),
                            color = MaterialTheme.colors.background
                        ) {
                            when (listType) {
                                List.ROW -> {
                                    TestRow()
                                }
                                List.COLUMN -> {
                                    TestColumn()
                                }
                                List.SPANNABLE -> {
                                    TestSpannable()
                                }
                                List.TEXT_LIST -> {
                                    TestTextList()
                                }
                                List.WEBVIEW -> {
                                    TestWebView()
                                }
                            }
                        }
                    }
                )

            }
        }
    }
}

enum class List {
    ROW,
    COLUMN,
    SPANNABLE,
    TEXT_LIST,
    WEBVIEW
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
    val columnItems = listItems
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

@Composable
fun TestSpannable() {
    Text(
        text = buildAnnotatedString {
            listItems.toBulletedList()
        },
        modifier = Modifier.padding(12.dp),
        style = TextStyle(fontSize = 24.sp)
    )
}

@Composable
fun TestTextList() {
    Text(
        text = stringResource(id = R.string.list_string),
        modifier = Modifier.padding(12.dp),
        style = TextStyle(fontSize = 24.sp)
    )
}


@Composable
fun TestWebView() {
    val state = WebViewState(WebContent.Data(data =
    "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<body>\n" +
            "\n" +
            "<h2>Unordered List with Disc Bullets</h2>\n" +
            "\n" +
            "<ul>\n" +
            "  <li>Coffee</li>\n" +
            "  <li>Tea</li>\n" +
            "  <li>Milk</li>\n" +
            "</ul>  \n" +
            "\n" +
            "</body>\n" +
            "</html>\n"
    )
    )


    WebView(
        state
    )

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AccessibilityFunTheme {
        TestRow()
    }
}

fun kotlin.collections.List<String>.toBulletedList(): CharSequence {
    return SpannableString(this.joinToString("\n")).apply {
        this@toBulletedList.foldIndexed(0) { index, string, span ->
            val end = string + span.length + if (index != this@toBulletedList.size - 1) 1 else 0
            this.setSpan(BulletSpan(16), string, end, 0)
            end
        }
    }
}