package at.ac.htlleonding.calculator

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.ac.htlleonding.calculator.ui.theme.DarkGreen
import at.ac.htlleonding.calculator.ui.theme.LightGray
import at.ac.htlleonding.calculator.ui.theme.Orange

data class CalculatorButton(
    val symbol: String,
    val backgroundColor: Color,
    val width: Int = 1,
    val action: CalculatorAction
)

@Composable
fun Calculator(
    state: CalculatorState,
    modifier: Modifier = Modifier,
    buttonSpacing: Dp = 8.dp,
    onAction: (CalculatorAction) -> Unit
) {
    // Define button layout
    val buttons = listOf(
        // Row 1
        listOf(
            CalculatorButton("AC", LightGray, 2, CalculatorAction.Clear),
            CalculatorButton("Del", Color.Red, 1, CalculatorAction.Delete),
            CalculatorButton("/", Orange, 1, CalculatorAction.Operation(CalculatorOperation.Divide))
        ),
        // Row 2
        listOf(7, 8, 9).map { num ->
            CalculatorButton(
                num.toString(),
                Color.DarkGray,
                1,
                CalculatorAction.Number(num)
            )
        } + CalculatorButton(
            "*",
            Orange,
            1,
            CalculatorAction.Operation(CalculatorOperation.Multiply)
        ),
        // Row 3
        listOf(4, 5, 6).map { num ->
            CalculatorButton(
                num.toString(),
                Color.DarkGray,
                1,
                CalculatorAction.Number(num)
            )
        } + CalculatorButton(
            "-",
            Orange,
            1,
            CalculatorAction.Operation(CalculatorOperation.Subtract)
        ),
        // Row 4
        listOf(1, 2, 3).map { num ->
            CalculatorButton(
                num.toString(),
                Color.DarkGray,
                1,
                CalculatorAction.Number(num)
            )
        } + CalculatorButton("+", Orange, 1, CalculatorAction.Operation(CalculatorOperation.Add)),
        // Row 5
        listOf(
            CalculatorButton("0", Color.DarkGray, 2, CalculatorAction.Number(0)),
            CalculatorButton(".", Color.DarkGray, 1, CalculatorAction.Decimal),
            CalculatorButton("=", DarkGreen, 1, CalculatorAction.Calculate)
        )
    )

    Box(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter),
            verticalArrangement = Arrangement.spacedBy(buttonSpacing)
        ) {
            // Display
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 32.dp)
                    .horizontalScroll(rememberScrollState()), // Add horizontal scroll
                horizontalArrangement = Arrangement.End // Align content to the end
            ) {
                Text(
                    text = state.number1 + (state.operation?.symbol ?: "") + state.number2,
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Light,
                    fontSize = 80.sp,
                    color = Color.White,
                )
            }

            // Button grid
            buttons.forEach { rowButtons ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(buttonSpacing)
                ) {
                    rowButtons.forEach { button ->
                        CalculatorButton(
                            symbol = button.symbol,
                            modifier = Modifier
                                .background(button.backgroundColor)
                                .aspectRatio(ratio = button.width.toFloat())
                                .weight(button.width.toFloat()),
                            onClick = { onAction(button.action) }
                        )
                    }
                }
            }
        }
    }
}