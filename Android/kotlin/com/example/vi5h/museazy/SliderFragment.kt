package com.example.vi5h.museazy


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_slider.*


class SliderFragment : Fragment()
{

    var pageTitle : String = ""
    var pageDes : String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_slider, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fragment_title.text = pageTitle
        fragment_desc.text = pageDes
    }

    fun setTitle(title : String)
    {
        pageTitle = title
    }

    fun setDesc(desc : String)
    {
        pageDes = desc
    }

}
