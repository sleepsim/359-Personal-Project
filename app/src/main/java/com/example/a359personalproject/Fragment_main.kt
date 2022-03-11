package com.example.a359personalproject

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox


class Fragment_main : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment


        val fragmentView = inflater.inflate(R.layout.fragment_main,
        container,
        false)

        val checkBox = fragmentView.findViewById<CheckBox>(R.id.checkBoxTester)

        checkBox.setOnClickListener {
            checkBox.text = "LOOOOL"
        }

        val getCats: ArrayList<ListCategory> = MainActivity().getCat()

        return fragmentView
    }

    companion object {
        fun newInstance() : Fragment_main {
            return Fragment_main()
        }

    }

}