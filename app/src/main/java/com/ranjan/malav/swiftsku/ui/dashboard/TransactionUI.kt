package com.ranjan.malav.swiftsku.ui.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.ranjan.malav.swiftsku.ui.utils.UiUtils.formatAmount
import com.ranjan.malav.swiftsku.ui.utils.UiUtils.formatTime
import com.ranjan.malav.swiftsku.ui.utils.percent


@Composable
fun TransactionDialog(
    viewModel: DashboardViewModel, hideTrxs: () -> Unit
) {
    val transactions by viewModel.transactions.collectAsState(initial = emptyList())

    Dialog(
        onDismissRequest = { hideTrxs() },
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .padding(16.dp)
                .width(45.percent)
                .background(Color.White),
            elevation = 8.dp
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFFF6F6F6))
                        .padding(8.dp, 0.dp, 8.dp, 0.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Txn History", fontWeight = FontWeight.W700, fontSize = 16.sp
                    )
                    IconButton(onClick = { hideTrxs() }) {
                        Icon(Icons.Default.Clear, contentDescription = "Close")
                    }
                }
                Divider(color = Color(0xFFD8D8D8), thickness = 1.dp)
                TransactionsHeader()
                Divider(color = Color(0xFFD8D8D8), thickness = 1.dp)
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp, 0.dp, 8.dp, 0.dp)
                ) {
                    items(transactions) { item ->
                        Column(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(0.dp, 4.dp, 0.dp, 4.dp),
                            ) {
                                Text(
                                    text = "${item.txnId}",
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = formatTime(item.txnEndTime),
                                    modifier = Modifier.weight(1f)
                                )
                                Text(
                                    text = "${item.txnItems.size}",
                                    modifier = Modifier.weight(1f)
                                )
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(text = formatAmount(item.txnTotalGrandAmount))
                                    Icon(Icons.Default.KeyboardArrowRight, contentDescription = "")
                                }
                            }
                            Divider(color = Color(0xFFD8D8D8), thickness = 1.dp)
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun TransactionsHeader() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Transaction No.",
                color = Color(0xFF717A87),
                fontWeight = FontWeight.W500,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Time",
                color = Color(0xFF717A87),
                fontWeight = FontWeight.W500,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "#Items",
                color = Color(0xFF717A87),
                fontWeight = FontWeight.W500,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = "Total amount",
                color = Color(0xFF717A87),
                fontWeight = FontWeight.W500,
                modifier = Modifier.weight(1f)
            )
        }
    }
}