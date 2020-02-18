package com.example.googlerecaptcha

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_works.*



class WorksActivity : AppCompatActivity() {
    lateinit var mFragmentList: WorkFragmentList
    var workId:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_works)
        workId=intent.getIntExtra("WordId",0)
        mFragmentList=WorkFragmentList()
        initFragment()
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
    private fun initFragment() {
        var action = this.supportFragmentManager!!.beginTransaction()
        action.replace(
            R.id.worksFragmentContainer,
            mFragmentList.mMenuFragment
        )
        action.commit()
    }
}
