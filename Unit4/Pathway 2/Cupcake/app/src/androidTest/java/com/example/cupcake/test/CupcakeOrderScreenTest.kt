package com.example.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.cupcake.data.OrderUiState
import com.example.cupcake.ui.OrderSummaryScreen
import com.example.cupcake.ui.SelectOptionScreen
import com.example.cupcake.ui.StartOrderScreen
import org.junit.Rule
import org.junit.Test

class CupcakeOrderScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun selectOptionScreen_nextButton() {
        val flavors = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        val subtotal = "$100"
        composeTestRule.setContent {
            SelectOptionScreen(subtotal = subtotal, options = flavors)
        }
        composeTestRule.onNodeWithText("Vanilla").performClick()
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.next).assertIsEnabled()
    }

    @Test
    fun selectOptionScreen_verifyContent() {

        val flavors = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        val subtotal = "$100"
        composeTestRule.setContent {
            SelectOptionScreen(subtotal = subtotal, options = flavors)
        }

        flavors.forEach { flavor ->
            composeTestRule.onNodeWithText(flavor).assertIsDisplayed()
        }

        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                com.example.cupcake.R.string.subtotal_price,
                subtotal
            )
        ).assertIsDisplayed()

        composeTestRule.onNodeWithStringId(
            com.example.cupcake.R.string.next
        ).assertIsNotEnabled()
    }

    @Test
    fun startOrderScreen_verifyContent() {
        val quantityOptions = listOf(
            Pair(com.example.cupcake.R.string.one_cupcake, 1),
            Pair(com.example.cupcake.R.string.six_cupcakes, 6),
            Pair(com.example.cupcake.R.string.twelve_cupcakes, 12)
        )
        composeTestRule.setContent {
            StartOrderScreen(quantityOptions = quantityOptions,
                onNextButtonClicked = {}
            )
        }
        composeTestRule.onNodeWithContentDescription(null.toString())
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(com.example.cupcake.R.string.order_cupcakes)
        ).assertIsDisplayed()

        quantityOptions.forEach { option ->
            composeTestRule.onNodeWithText(
                composeTestRule.activity.getString(option.first)
            ).assertIsDisplayed()
        }
        quantityOptions.forEach { option ->
            composeTestRule.onNodeWithStringId(option.first).assertIsEnabled()
        }
    }

    @Test
    fun summaryScreen_verifyContent() {
        val orderUiState = OrderUiState(
            quantity = 6,
            flavor = "Vanilla",
            date = "Tomorrow",
            price = "$25.00"
        )
        composeTestRule.setContent {
            OrderSummaryScreen(
                orderUiState = orderUiState,
                onSendButtonClicked = { _, _ -> },
                onCancelButtonClicked = {}
            )
        }
        composeTestRule.onNodeWithText("QUANTITY").assertIsDisplayed()
        composeTestRule.onNodeWithText(orderUiState.quantity.toString()).assertIsDisplayed()
        composeTestRule.onNodeWithText("FLAVOR").assertIsDisplayed()
        composeTestRule.onNodeWithText(orderUiState.flavor).assertIsDisplayed()
        composeTestRule.onNodeWithText("PICKUP DATE").assertIsDisplayed()
        composeTestRule.onNodeWithText(orderUiState.date).assertIsDisplayed()
        composeTestRule.onNodeWithText(orderUiState.price).assertIsDisplayed()
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.send).assertIsEnabled()
        composeTestRule.onNodeWithStringId(com.example.cupcake.R.string.cancel).assertIsEnabled()
    }
}