package ca.com.freshworks.giphy.ui.base

import android.util.Log
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel(), LifecycleObserver {

    private val _loading = MutableLiveData<Boolean>()
    val loading get() = _loading

    fun launchWithCoroutineScope(
        enableLoading: Boolean = true,
        block: suspend CoroutineScope.() -> Unit
    ): Job {
        return viewModelScope.launch {
            try {
                if (enableLoading) {
                    loading.postValue(true)
                }
                block()
            } catch (throwable: Throwable) {
                Log.e("error", null, throwable)
            } finally {
                if (enableLoading) {
                    loading.postValue(false)
                }
            }
        }
    }
}