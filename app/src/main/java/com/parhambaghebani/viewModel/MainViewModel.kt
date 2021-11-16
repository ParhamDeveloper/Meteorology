package com.parhambaghebani.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.parhambaghebani.model.MainRepository
import com.parhambaghebani.utility.Response
import com.parhambaghebani.utility.model.AirTemperatureModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: MainRepository) : ViewModel() {

    private val viewModelJob = SupervisorJob()
    private val ioScope = CoroutineScope(Dispatchers.IO + viewModelJob)

    val data: LiveData<Response<AirTemperatureModel?>>
        get() = repository.data

    fun onMapClick(latitude: Double, longitude: Double) {
        ioScope.launch {
            repository.onMapClick(latitude, longitude)
        }
    }

    override fun onCleared() {
        viewModelJob.cancel()
        super.onCleared()
    }
}