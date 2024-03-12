package com.ranjan.malav.swiftsku.ui.dashboard

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ranjan.malav.swiftsku.data.model.TransactionItem
import com.ranjan.malav.swiftsku.ui.utils.UiUtils.formatAmount
import com.ranjan.malav.swiftsku.ui.utils.percent


@Composable
fun DashboardLayout(
    viewModel: DashboardViewModel
) {
    val cartItems by viewModel.selectedItemsLive.collectAsState()
    val totals by viewModel.totals.collectAsState()

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        LeftSection(cartItems, totals)
        RightSection()
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
                    .padding(0.dp, 8.dp, 0.dp, 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = item.pluItem.itemName,
                    modifier = Modifier.weight(2f)
                )
                Box(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 10.dp)
                        .weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "x${item.quantity}",
                        modifier = Modifier
                            .wrapContentWidth(Alignment.CenterHorizontally)
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
                    text = "${totals.subTotalAmount}",
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
                    text = "${totals.taxAmount}",
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
                    text = "${totals.discountAmount}",
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
                    text = "${totals.grandTotal}",
                    fontWeight = FontWeight.W700,
                    modifier = Modifier
                        .wrapContentWidth(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
@Preview
fun RightSection() {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.White)
    ) {
        // Content of right section
        Text(
            text = "Right Section",
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
@Preview
fun ActionsBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { /* Handle Save button click */ },
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(text = "Save")
            }
            Button(
                onClick = { /* Handle Recall button click */ },
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(text = "Recall")
            }
            Button(
                onClick = { /* Handle Txn History button click */ },
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(text = "Txn History")
            }
            Button(
                onClick = { /* Handle Complete button click */ },
                modifier = Modifier.padding(start = 16.dp)
            ) {
                Text(text = "Complete")
            }
        }
        Text(
            text = "Aug 2, 2022 18:23AM",
            style = MaterialTheme.typography.body1,
            modifier = Modifier.padding(vertical = 16.dp)
        )
    }
}

@Composable
@Preview
fun CartHeading() {
    Row(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Fountain-32oz",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.weight(2f)
        )
        Box(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 10.dp)
                .weight(1f)
                .background(
                    color = Color(0xFFE3E3E3),
                    shape = RoundedCornerShape(100.dp)
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "x27",
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
        }
        Text(
            text = "$42.93",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.End,
            modifier = Modifier.weight(1f)
        )
    }
}