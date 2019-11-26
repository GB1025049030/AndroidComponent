package com.wangzhen.moudule_one.business

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wangzhen.moudule_one.R
import com.wangzhen.moudule_one.business.adapter.StudentAdapter
import com.wangzhen.moudule_one.business.mvp.StudentModel
import kotlinx.android.synthetic.main.activity_one.*

class OneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_one)

        val adapter = StudentAdapter()
        studentList.adapter = adapter
        studentList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        adapter.setData(mockData())
    }

    private fun mockData(): List<StudentModel> {
        return mutableListOf(
            StudentModel("变有钱", 26),
            StudentModel("不喝奶茶了", 29),
            StudentModel("内定中奖者", 29),
            StudentModel("爱喝不喝", 22)
        )
    }
}
