package com.example.vi5h.museazy
import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity()
{

    override fun onCreate(savedInstanceState: Bundle?)
    {
        val fragment = SliderFragment()
        val fragment2 = SliderFragment()
        val fragment3 = SliderFragment()
        val showIntro = "Intro"
        lateinit var adapter: myPageAdapter
        lateinit var activity: Activity
        lateinit var preferences: SharedPreferences

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activity = this
        preferences = getSharedPreferences("IntroSlides", Context.MODE_PRIVATE)
        if(!preferences.getBoolean(showIntro, true))
        {
            startActivity(Intent(activity, DashboardActivity::class.java))
            finish()
        }

        //Slide One
        fragment.setTitle("Welcome to Museazy!")
        fragment.setDesc("Museazy contains wide range of musical courses from the Web all at one place for you " +
                "to start learning. Weather it is a hobby or you just looking to try something new; we have got you " +
                "covered as we find variety of courses from different musical Genres. As well as learning you " +
                "can find singers, producers or vocalists for your track or project through this app.")

        //Side Two
        fragment2.setTitle("Quick start guide")
        fragment2.setDesc("You can either skip or go through these introductory slides, however once you’re " +
                "past these slides you will have a good understanding of all the features of Museazy. " +
                "Your profile Dashboard can be accessed after the end of these slides. There you will " +
                "find features where you can Learn music, Find other musical folks or even rent or hire " +
                "musical equipment’s related to music. Have fun using Museazy!")

        //Slide Three
        fragment3.setTitle("About Us")
        fragment3.setDesc("The first part of Mus which refers to  – “Music” and the second part eazy, " +
                "well ‘eazy’ So all together Museazy which is a application which helps you having to " +
                "go through a lot of digging to learn for find use elements regarding music making " +
                "or learning to write or play music.")

        adapter = myPageAdapter(supportFragmentManager)
        adapter.list.add(fragment)
        adapter.list.add(fragment2)
        adapter.list.add(fragment3)

        view_pager.adapter = adapter

        btnNext.setOnClickListener {
            view_pager.currentItem++
        }
        indicator1.setImageResource(R.drawable.ic_radio_button_checked_black_24dp)

        btnSkip.setOnClickListener {
            startActivity(Intent(activity, DashboardActivity::class.java))
            finish()
            val editor = preferences.edit()
            editor.putBoolean(showIntro, false)
            editor.apply()
        }

        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener{
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                if (position == adapter.list.size-1){
                    //last page
                    btnNext.text = "DONE"
                    btnSkip.isEnabled = false
                    btnNext.setOnClickListener {
                        startActivity(Intent(activity, DashboardActivity::class.java))
                        finish()
                        val editor = preferences.edit()
                        editor.putBoolean(showIntro, false)
                        editor.apply()
                    }
                } else {
                    // has next page
                    btnNext.text = "Next"
                    btnSkip.isEnabled = true
                    btnNext.setOnClickListener {
                        view_pager.currentItem++
                    }
                }
                when (view_pager.currentItem) {
                    0 -> {
                        indicator1.setImageResource(R.drawable.ic_radio_button_checked_black_24dp)
                        indicator2.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp)
                        indicator3.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp)
                    }
                    1 -> {
                        indicator1.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp)
                        indicator2.setImageResource(R.drawable.ic_radio_button_checked_black_24dp)
                        indicator3.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp)
                    }
                    2 -> {
                        indicator1.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp)
                        indicator2.setImageResource(R.drawable.ic_radio_button_unchecked_black_24dp)
                        indicator3.setImageResource(R.drawable.ic_radio_button_checked_black_24dp)
                    }
                }
            }
        })
    }
    class myPageAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager){
        val list : MutableList<android.support.v4.app.Fragment> = ArrayList()

        override fun getItem(position: Int): android.support.v4.app.Fragment
        {
            return list[position]
        }

        override fun getCount(): Int {
            return list.size
        }
    }
}
