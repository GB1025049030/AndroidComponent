package com.wangzhen.androidcomponent

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.luojilab.component.componentlib.router.Router
import com.wangzhen.baselib.http.ApiClient
import com.wangzhen.data.github.RepoSearchResponse
import com.wangzhen.moudule_one.api.service.OneRouterService
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.InvocationTargetException

class MainActivity : AppCompatActivity() {
    var textView: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bind()
        textView?.text = "bind success"
        btToOneComponent.setOnClickListener {
            Router.getInstance().getService(OneRouterService::class.java).launchOneActivity(this)
        }

        btSearch.setOnClickListener {

        }
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
