package com.wangzhen.androidcomponent

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.luojilab.component.componentlib.router.Router
import com.wangzhen.moudule_one.api.service.OneRouterService
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.reflect.InvocationTargetException

class MainActivity : AppCompatActivity() {
    var textView:TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bind()
        textView?.text = "bind success"
        btToOneComponent.setOnClickListener {
            Router.getInstance().getService(OneRouterService::class.java).launchOneActivity(this)
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
