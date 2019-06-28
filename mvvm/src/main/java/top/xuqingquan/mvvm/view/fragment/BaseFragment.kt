package top.xuqingquan.mvvm.view.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import top.xuqingquan.base.view.fragment.SimpleFragment
import top.xuqingquan.mvvm.viewModel.BaseViewModel

/**
 * Created by 许清泉 on 2019-04-21 00:40
 */
abstract class BaseFragment: SimpleFragment() {

    protected var viewModel: BaseViewModel? = null
        get() {
            if (field == null) {
                field = getVM()
            }
            return field!!
        }
        private set

    protected var binding: ViewDataBinding? = null
        get() {
            if (field == null) {
                field = getBD()
            }
            return field!!
        }
        private set

    final override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false)
        return binding!!.root
    }

    protected abstract fun <VM : BaseViewModel> getVM(): VM?

    protected abstract fun <VDB : ViewDataBinding> getBD(): VDB

    override fun initView(view: View) {}

    override fun onDestroy() {
        super.onDestroy()
        binding!!.unbind()
    }
}