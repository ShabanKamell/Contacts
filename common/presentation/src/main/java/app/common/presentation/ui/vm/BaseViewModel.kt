package app.common.presentation.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.common.data.DataManager
import app.common.presentation.requester.AppRequester
import com.sha.coroutinerequester.Presentable
import com.sha.coroutinerequester.RequestOptions
import io.reactivex.disposables.CompositeDisposable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

open class BaseViewModel(val dm: DataManager) : ViewModel() {

    val disposables: CompositeDisposable = CompositeDisposable()
    val toggleLoading = MutableLiveData<Boolean>()
    val showError = MutableLiveData<String>()
    val showErrorRes = MutableLiveData<Int>()

    private val requester: AppRequester by lazy {
        val presentable = object : Presentable {
            override fun showError(error: String) {
                showError.value = error
            }

            override fun showError(error: Int) {
                showErrorRes.value = error
            }

            override fun showLoading() {
                toggleLoading.value = true
            }

            override fun hideLoading() {
                toggleLoading.value = false
            }

            override fun onHandleErrorFailed(throwable: Throwable) {
                showError.value = throwable.message
            }
        }
        AppRequester(presentable)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    /**
     * Each request should call this function to apply Coroutines and
     * handle exceptions
     */
    fun request(
        options: RequestOptions = RequestOptions.default(),
        context: CoroutineContext = Dispatchers.IO,
        block: suspend () -> Unit
    ) {
        viewModelScope.launch(context) {
            requester.request(options, block)
        }
    }
}

