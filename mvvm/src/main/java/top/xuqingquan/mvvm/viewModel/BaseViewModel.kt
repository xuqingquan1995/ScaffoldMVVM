package top.xuqingquan.mvvm.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import top.xuqingquan.BuildConfig
import top.xuqingquan.base.model.repository.IRepository
import kotlin.coroutines.CoroutineContext

/**
 * Created by 许清泉 on 2019-04-20 22:40
 */
abstract class BaseViewModel<R : IRepository> : ViewModel() {
    protected var repository: R? = null
        get() {
            if (field == null) {
                field = getIRepository()
            }
            return field
        }
        private set

    override fun onCleared() {
        super.onCleared()
        repository?.onDestroy()
        viewModelScope.cancel()
    }

    fun <T> launch(
        context: CoroutineContext = Dispatchers.Default,
        tryBlock: suspend CoroutineScope.() -> T,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit = {},
        finallyBlock: suspend CoroutineScope.() -> Unit = {}
    ): Job {
        return viewModelScope.launch(context) {
            try {
                tryBlock()
            } catch (e: Throwable) {
                if (BuildConfig.DEBUG) {
                    e.printStackTrace()
                }
                catchBlock(e)
            } finally {
                finallyBlock()
            }
        }
    }

    fun <T> launch(context: CoroutineContext = Dispatchers.Default, tryBlock: suspend CoroutineScope.() -> T): Job {
        return launch(context, tryBlock, {}, {})
    }

    protected abstract fun getIRepository(): R?

}