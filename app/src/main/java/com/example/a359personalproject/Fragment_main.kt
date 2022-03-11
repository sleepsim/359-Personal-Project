package com.example.a359personalproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout


class Fragment_main : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val fragmentView = inflater.inflate(R.layout.fragment_main,
        container,
        false)

        //Grab current category from main activity
        val currCat: ListCategory = (activity as MainActivity).getCat()
        currCat.getItems()

        val checkBox = fragmentView.findViewById<CheckBox>(R.id.checkBoxTester)

        checkBox.setOnClickListener {
            checkBox.text = "LOOOOL"
        }

        if(currCat.getItems().size>0 && currCat.getItems().get(0)!= null) {
            val cb: CheckBox = CheckBox(activity)
            val ll = fragmentView.findViewById<LinearLayout>(R.id.linearLayout)
            cb.text = currCat.getItems().get(0)
            ll.addView(cb)
            Log.i("Index size", currCat.getItems().size.toString())
            cb.setOnClickListener {
                (activity as MainActivity).removeItem(currCat.getItems().get(0))
            }
        }


        if(currCat.getCategoryName() == "Trash") checkBox.text = "TRASH TRASH TRASH"
        Log.i("FragmentRefresh", currCat.getCategoryName())

        return fragmentView
    }

    companion object {
        fun newInstance() : Fragment_main {
            return Fragment_main()
        }
    }

}