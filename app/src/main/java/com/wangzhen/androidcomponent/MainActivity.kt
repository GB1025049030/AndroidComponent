package com.wangzhen.androidcomponent

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.luojilab.component.componentlib.router.Router
import com.wangzhen.annotation.Student
import com.wangzhen.moudule_one.api.service.OneRouterService
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    @Student(age = 10,height = 180f)
    private val name = "zhang"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btToOneComponent.setOnClickListener {
            Router.getInstance().getService(OneRouterService::class.java).launchOneActivity(this)
        }
    }
}
