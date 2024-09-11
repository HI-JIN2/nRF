package com.eddy.nrf.presentation.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eddy.nrf.utils.Util
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class BikeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(BikeUiState())
    val uiState: StateFlow<BikeUiState> = _uiState.asStateFlow()

    fun changeGear(gear: Int) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    gear = gear
                )
            }
            //여긴 잘 바뀜
            Timber.d("기어값이 바뀌었습니다. : $gear   ${uiState.value.gear}")
        }
    }

    fun changeSpeed(proportionalFactor: Float) {
        viewModelScope.launch {
            val afterSpeed =
                Util.calculateSpeed(uiState.value.speed, uiState.value.gear, proportionalFactor)

            _uiState.update {
                it.copy(
                    speed = afterSpeed,
                )
            }
            Timber.d("속도값이 바뀌었습니다. : $afterSpeed   ${uiState.value.gear}")
        }
    }

    fun changeSpeed(gear: Int) {
        viewModelScope.launch {
            val afterSpeed =
                Util.calculateSpeed(uiState.value.speed, gear, uiState.value.proportionalFactor)

            _uiState.update {
                it.copy(
                    speed = afterSpeed,
                )
            }
            Timber.d("속도값이 바뀌었습니다. : $afterSpeed   ${uiState.value.gear}")
        }
    }
}