package com.example.accessibilityfun

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.SpannableString
import android.text.Spanned
import android.text.SpannedString
import android.text.method.LinkMovementMethod
import android.text.style.BulletSpan
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.*
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.accessibilityfun.ui.theme.AccessibilityFunTheme
import com.google.accompanist.web.*
import java.util.regex.Pattern

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
//                            navigationIcon = {
//                                IconButton(onClick = {
//
//                                }) {
//                                    Icon(Icons.Rounded.ArrowBack, "navigate back")
//                                }
//                            },
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
                                        showMenu = false
                                        listType = List.ROW
                                    }) {
                                        Text("Row")
                                    }
                                    DropdownMenuItem(onClick = {
                                        showMenu = false
                                        listType = List.COLUMN
                                    }) {
                                        Text("Column")
                                    }
                                    DropdownMenuItem(onClick = {
                                        showMenu = false
                                        listType = List.SPANNABLE
                                    }) {
                                        Text(List.SPANNABLE.toString())
                                    }
                                    DropdownMenuItem(onClick = {
                                        showMenu = false
                                        listType = List.TEXT_LIST
                                    }) {
                                        Text(List.TEXT_LIST.toString())
                                    }
                                    DropdownMenuItem(onClick = {
                                        showMenu = false
                                        listType = List.WEBVIEW
                                    }) {
                                        Text(List.WEBVIEW.toString())
                                    }
                                    DropdownMenuItem(onClick = {
                                        showMenu = false
                                        listType = List.WEBVIEW
                                    }) {
                                        Text(List.WEBVIEW.toString())
                                    }
                                    DropdownMenuItem(onClick = {
                                        showMenu = false
                                        listType = List.CLICKABLE_VIEW
                                    }) {
                                        Text(List.CLICKABLE_VIEW.toString())
                                    }
                                    DropdownMenuItem(onClick = {
                                        showMenu = false
                                        listType = List.LINKIFY_TEXT
                                    }) {
                                        Text(List.LINKIFY_TEXT.toString())
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
                                List.CLICKABLE_VIEW -> {
                                    ArticleCard {

                                    }
                                }
                                List.LINKIFY_TEXT -> {
                                    LinkifyText()
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
    WEBVIEW,
    CLICKABLE_VIEW,
    LINKIFY_TEXT
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
    val bullet = "\u2022"
    val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 12.sp))
    Text(
        text = buildAnnotatedString {
            listItems.forEach {
                withStyle(style = paragraphStyle) {
                    append(bullet)
                    append("\t\t")
                    append(it)
                }
            }
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
fun LinkifyText() {
    HtmlTextView(text = tosText)
}


@Composable
fun TestWebView() {
    val state = WebViewState(WebContent.Data(data = tosText))

    WebView(
        state
    )

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleCard(openArticle: () -> Unit) {
    Card(
        onClick = openArticle,
        // R.string.action_read_article = "read article"
        onClickLabel = "read article"
    ) {
        Text("Article A")
    }
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

val tosText = "<p>Use your <a href=\"http://amazon.com/\">Amazon.com</a> Gift Card* towards Books, Electronics, Music, and more. The <a href=\"http://amazon.com/\">Amazon.com</a> web site is the place to find and discover almost any thing you want to buy online at a great price.</p>\n" +
        "\n" +
        "<p>Restrictions apply, see <a href=\"https://www.amazon.com/gp/help/customer/display.html/ref=s9_acss_bw_tm_BGMDT7_md1_w?nodeId=3122091&pf_rd_m=ATVPDKIKX0DER&pf_rd_s=merchandised-search-8&pf_rd_r=1FJP0A5NSBGJC5AJC6RK&pf_rd_t=101&pf_rd_p=89081f45-f3a3-44e0-8747-edb0acca2333&pf_rd_i=17238247011\">amazon.com/gc-legal</a></p>\n" +
        "\n" +
        "<p>Restrictions apply, see <a href=\"https://www.amazon.com/gp/help/customer/display.html/ref=s9_acss_bw_tm_BGMDT7_md1_w?nodeId=3122091&pf_rd_m=ATVPDKIKX0DER&pf_rd_s=merchandised-search-8&pf_rd_r=1FJP0A5NSBGJC5AJC6RK&pf_rd_t=101&pf_rd_p=89081f45-f3a3-44e0-8747-edb0acca2333&pf_rd_i=17238247011\">amazon.com/gc-legal</a></p>\n"

val tosText2 = "Use your Amazon.com Gift Card* towards Books, Electronics, Music, and more. The Amazon.com web site is the place to find and discover almost any thing you want to buy online at a great price.\n" +
        "\n" +
        "Restrictions apply, see amazon.com/gc-legal\n" +
        "\n" +
        "Restrictions apply, see amazon.com/gc-legal"


@Composable
fun linkifyText(text: String, textToLink: String, link: String, linkStyle: TextStyle) =
    buildAnnotatedString {
        val startIndex = text.indexOf(textToLink)
        val endIndex = startIndex + textToLink.length
        append(text)
        addStyle(
            style = SpanStyle(
                color = MaterialTheme.colors.primary,
                fontSize = linkStyle.fontSize,
                textDecoration = TextDecoration.Underline
            ), start = startIndex, end = endIndex
        )

        // attach a string annotation that stores a URL to the text "link"
        addStringAnnotation(
            tag = "WOOT",
            annotation = link,
            start = startIndex,
            end = endIndex
        )
    }


@Composable
fun LinkifyText(text: String, modifier: Modifier = Modifier) {
    val uriHandler = LocalUriHandler.current
    val layoutResult = remember {
        mutableStateOf<TextLayoutResult?>(null)
    }
    val linksList = extractUrls(text)
    val annotatedString = buildAnnotatedString {
        append(text)
        linksList.forEach { it : LinkInfos ->
            addStyle(
                style = SpanStyle(
                    color = Color.Companion.Blue,
                    textDecoration = TextDecoration.Underline
                ),
                start = it.start,
                end = it.end
            )
            addStringAnnotation(
                tag = "URL",
                annotation = it.url,
                start = it.start,
                end = it.end
            )
        }
    }
    Text(text = annotatedString, style = MaterialTheme.typography.body1, modifier = modifier.pointerInput(Unit) {
        detectTapGestures { offsetPosition ->
            layoutResult.value?.let {
                val position = it.getOffsetForPosition(offsetPosition)
                annotatedString.getStringAnnotations(position, position).firstOrNull()
                    ?.let { result ->
                        if (result.tag == "URL") {
                            uriHandler.openUri(result.item)
                        }
                    }
            }
        }
    },
        onTextLayout = { layoutResult.value = it }
    )
}

private val urlPattern: Pattern = Pattern.compile(
    "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
            + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
            + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
    Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
)

fun extractUrls(text: String): ArrayList<LinkInfos> {
    val matcher = urlPattern.matcher(text)
    var matchStart: Int
    var matchEnd: Int
    val links = arrayListOf<LinkInfos>()

    while (matcher.find()) {
        matchStart = matcher.start(1)
        matchEnd = matcher.end()

        var url = text.substring(matchStart, matchEnd)
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "https://$url"

        links.add(LinkInfos(url, matchStart, matchEnd))
    }
    return links
}

data class LinkInfos(
    val url: String,
    val start: Int,
    val end: Int
)


@Composable
fun HtmlTextView(modifier: Modifier = Modifier, text: String) {
    val context = LocalContext.current
    val customLinkifyTextView = remember {
        TextView(context)
    }
    AndroidView(modifier = modifier, factory = { customLinkifyTextView }) { textView ->
        textView.text = text.fromHtml()
        textView.movementMethod = LinkMovementMethod.getInstance()
    }
}

@Suppress("deprecation")
fun String?.fromHtml(): Spanned {
    return this?.let { htmlString ->
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(htmlString)
        }
    } ?: SpannedString("")
}
