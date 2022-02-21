package pl.marczak.eventsconsumer

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel() as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.plant(Timber.DebugTree())
        setContentView(R.layout.activity_main)


        // 1. asLiveData
//        viewModel.events.asLiveData().observe(this) {
//            renderEvent("asLiveData", it)
//        }

        // 2. launchWhenResumed
        lifecycleScope.launchWhenResumed {
            viewModel.events.collect {
                renderEvent("launchWhenResumed", it)
            }
        }
        lifecycleScope.launchWhenResumed {
            viewModel.viewState.collect {
                renderEvent("launchWhenResumed", it)
            }
        }

        // 3. repeatOnLifecycle
//        lifecycleScope.launch {
//            repeatOnLifecycle(Lifecycle.State.RESUMED) {
//                viewModel.events.collect {
//                    renderEvent("repeatOnLifecycle(RESUMED)", it)
//                }
//            }
//        }

        viewModel.eventsSample()
//        viewModel.viewStateSample()
    }

    private fun renderEvent(tag: String, event: String?) {
        Timber.w("$tag render event $event")
        Toast.makeText(this, event, Toast.LENGTH_SHORT)
            .show()
    }
}