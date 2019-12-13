package com.wangzhen.androidcomponent

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.luojilab.component.componentlib.router.Router
import com.wangzhen.androidcomponent.test.http.viewmodel.GithubViewModel
import com.wangzhen.annotation_lib.BindView
import com.wangzhen.baselib.http.ApiStatus
import com.wangzhen.baselib.http.Resource
import com.wangzhen.data.github.RepoSearchResponse
import com.wangzhen.moudule_one.api.service.OneRouterService
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.InvocationTargetException

class MainActivity : AppCompatActivity() {
    @BindView(value = R.id.tv_test)
    var textView: TextView? = null
//    private val viewModel by lazy(LazyThreadSafetyMode.NONE) {
//        ViewModelProviders.of(this).get(GithubViewModel::class.java)
//    }

    private var viewModel:GithubViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bind()
        textView?.text = "bind success"
        btToOneComponent.setOnClickListener {
            Router.getInstance().getService(OneRouterService::class.java).launchOneActivity(this)
        }

        btSearch.setOnClickListener {
            viewModel?.startSearch("android")
        }

        initViewModel()
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(GithubViewModel::class.java)
        viewModel?.reposLiveData?.observe(this,
            Observer<Resource<RepoSearchResponse>> {
                when (it.status) {
                    ApiStatus.LOADING -> {
                        Log.e("1---", "LOADING")
                    }

                    ApiStatus.LOCAL_SUCCESS -> {
                        Log.e("1---", "LOCAL_SUCCESS,${it.data?.total}")
                    }

                    ApiStatus.REMOTE_SUCCESS -> {
                        Log.e("1---", "REMOTE_SUCCESS,${it.data?.total}")
                    }

                    ApiStatus.ERROR -> {
                        Log.e("1---", "loading")
                    }
                }
            })
    }

    private fun bind() {
        try {
            val bindViewClazz = Class.forName(this.javaClass.name + "_ViewBinding")
            val method = bindViewClazz.getMethod("bind", this.javaClass)
            method.invoke(bindViewClazz.newInstance(), this)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        }
    }
}
