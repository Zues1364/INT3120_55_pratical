package com.example.dessertclicker.ui

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.data.Datasource.dessertList
import com.example.dessertclicker.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()
    private lateinit var dessertCurrent: Dessert


    private fun determineDessertToShow(): Dessert {
        dessertCurrent = dessertList.first()
        val dessertsSold = _uiState.value.dessertsSold
        for (dessert in dessertList) {
            if (dessertsSold >= dessert.startProductionAmount) {
                dessertCurrent = dessert
            } else {
                break
            }
        }
        return dessertCurrent
    }


    private fun getCurrentDessertId(dessert: Dessert): Int {
        return dessertList.indexOf(dessert)
    }


    fun updateAppUiState(currentRevenue: Int) {
        determineDessertToShow()
        val updatedRevenue = currentRevenue.plus(dessertCurrent.price)
        val currentDessertIndex = getCurrentDessertId(dessertCurrent)

        _uiState.update { currentState ->
            currentState.copy(
                revenue = updatedRevenue,
                dessertsSold = currentState.dessertsSold.inc(),
                currentDessertIndex = currentDessertIndex,
                currentDessertImageId = dessertList[currentDessertIndex].imageId,
                currentDessertPrice = dessertList[currentDessertIndex].price
            )
        }
    }
}
