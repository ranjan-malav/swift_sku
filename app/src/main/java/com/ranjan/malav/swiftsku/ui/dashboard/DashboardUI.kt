package com.ranjan.malav.swiftsku.ui.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontWeight.Companion.W500
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ranjan.malav.swiftsku.data.model.PriceBookItem
import com.ranjan.malav.swiftsku.data.model.Transaction
import com.ranjan.malav.swiftsku.data.model.TransactionItem
import com.ranjan.malav.swiftsku.ui.utils.UiUtils.formatAmount
import com.ranjan.malav.swiftsku.ui.utils.percent


@Composable
fun DashboardLayout(
    viewModel: DashboardViewModel
) {
    val cartItems by viewModel.selectedItemsLive.collectAsState()
    val totals by viewModel.totals.collectAsState()
    val transactions by viewModel.transactions.collectAsState()
    val bookItems by viewModel.bookItems.collectAsState()
    val itemClick: (item: PriceBookItem) -> Unit = {
        viewModel.selectItem(it)
    }
    var showTransactions by remember { mutableStateOf(false) }
    val showTrxs: () -> Unit = {
        showTransactions = true
    }

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        LeftSection(cartItems, totals)
        RightSection(
            transactions, bookItems, itemClick, viewModel, showTrxs
        )
        if (showTransactions) {
            TransactionDialog(viewModel, hideTrxs = { showTransactions = false })
        }
    }
}

@Composable
fun LeftSection(
    cartItems: ArrayList<TransactionItem>,
    totals: DashboardViewModel.Totals
) {
    Box(
        modifier = Modifier
            .width(24.percent)
            .fillMaxHeight()
            .background(Color(0xFFF6F6F6))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            CartHeader()
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                CartItems(cartItems)
            }
            TotalFooter(totals)
        }
    }
}

@Composable
@Preview
fun CartHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(BorderStroke(2.dp, Color(0xFFD8D8D8)))
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Description",
                fontWeight = FontWeight.W600,
                modifier = Modifier.weight(2f)
            )
            Text(
                text = "QTY",
                fontWeight = FontWeight.W600,
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
            Text(
                text = "Amount",
                fontWeight = FontWeight.W600,
                textAlign = TextAlign.End,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun CartItems(cartItems: ArrayList<TransactionItem>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 0.dp, 8.dp, 0.dp)
    ) {
        items(cartItems) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 4.dp, 0.dp, 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.pluItem.itemName,
                    modifier = Modifier.weight(2f)
                )
                Box(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 10.dp)
                        .wrapContentWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "x${item.quantity}",
                        modifier = Modifier
                            .background(
                                color = Color(0xFFE3E3E3),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(10.dp, 0.dp, 10.dp, 0.dp)
                    )
                }
                Text(
                    text = formatAmount(item.pluItem.price * item.quantity),
                    textAlign = TextAlign.End,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
fun TotalFooter(totals: DashboardViewModel.Totals) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .border(BorderStroke(2.dp, Color(0xFFD8D8D8)))
            .padding(16.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Sub Total",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = formatAmount(totals.subTotalAmount),
                    modifier = Modifier
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Tax",
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = formatAmount(totals.taxAmount),
                    modifier = Modifier
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "Discount",
                    color = Color(0xFF4BD461),
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = formatAmount(totals.discountAmount),
                    color = Color(0xFF4BD461),
                    modifier = Modifier
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            ) {
                Text(
                    text = "Grand  Total",
                    fontWeight = FontWeight.W700,
                    modifier = Modifier.weight(1f)
                )
                Text(
                    text = formatAmount(totals.grandTotal),
                    fontWeight = FontWeight.W700,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
fun RightSection(
    transactions: List<Transaction>,
    bookItems: List<PriceBookItem>,
    onItemClick: (item: PriceBookItem) -> Unit,
    viewModel: DashboardViewModel,
    showTrxs: () -> Unit
) {
    val totalSales = transactions.sumOf { it.txnTotalGrandAmount.toDouble() }
    val totalCount = transactions.size
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color(0xFFE1E1E1))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SalesHeader(totalSales.toFloat(), totalCount)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                BookItems(bookItems, onItemClick)
            }
            ActionFooter(viewModel, showTrxs)
        }
    }
}

@Composable
fun SalesHeader(totalSales: Float, trxCount: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF6F6F6))
    ) {
        Row {
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = "Total Sales",
                )
                Text(
                    fontWeight = FontWeight.W600,
                    text = formatAmount(totalSales),
                )
            }
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    text = "Customer Count",
                )
                Text(text = "$trxCount", fontWeight = FontWeight.W600)
            }
        }
    }
}

@Composable
fun BookItems(
    bookItems: List<PriceBookItem>,
    itemClick: (item: PriceBookItem) -> Unit
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(5),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(bookItems) { item ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = Color(0xFF513DA3))
                    .padding(8.dp)
                    .clickable(onClick = { itemClick(item) }),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = item.itemName,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ActionFooter(
    viewModel: DashboardViewModel,
    showTrxs: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color(0xFFF6F6F6))
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ClickableText(
            onClick = { viewModel.saveTransaction() },
            modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 8.dp),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(color = Color.Black, fontWeight = W500)
                ) {
                    append("Save")
                }
            }
        )
        ClickableText(
            onClick = { viewModel.recallTransaction() },
            modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 8.dp),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(color = Color.Black, fontWeight = W500)
                ) {
                    append("Recall")
                }
            }
        )
        ClickableText(
            onClick = { showTrxs() },
            modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 8.dp),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(color = Color.Black, fontWeight = W500)
                ) {
                    append("Txn History")
                }
            }
        )
        ClickableText(
            onClick = { viewModel.completeTransaction() },
            modifier = Modifier.padding(16.dp, 8.dp, 16.dp, 8.dp),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(color = Color.Black, fontWeight = W500)
                ) {
                    append("Complete")
                }
            }
        )
    }
}