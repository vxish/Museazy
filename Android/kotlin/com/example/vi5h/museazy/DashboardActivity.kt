package com.example.vi5h.museazy

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.ActionBarDrawerToggle
import android.view.View
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    private  var mDrawerToggle : ActionBarDrawerToggle? = null

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        mDrawerToggle = ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.Open,R.string.Close)
        mDrawerToggle!!.syncState()

    }

    fun update(view: View)
    {
        tv_content.text = (view as TextView).text
        drawerLayout!!.closeDrawer(8388611)




    }
}
