package pl.marczak.eventsconsumer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel : ViewModel() {

    private val _events = Channel<String>()

    private val _viewState = MutableStateFlow("")

    val events: Flow<String> = _events.receiveAsFlow()
    val viewState: Flow<String> = _viewState

    fun eventsSample() {
        viewModelScope.launch {
            for (j in 0..10) {
                val eventName = "event-$j"
                Timber.w("attempt to emit \'$eventName\'")
                _events.send(eventName)
                delay(1_500L)
            }
        }
    }

    fun viewStateSample() {
        viewModelScope.launch {
            for (j in 0..10) {
                val eventName = "event-$j"
                Timber.w("attempt to emit \'$eventName\'")
                _viewState.value = eventName
                delay(1_500L)
            }
        }
    }
}