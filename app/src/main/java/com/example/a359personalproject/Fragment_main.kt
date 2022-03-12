package com.example.a359personalproject

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.Toast


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

        //Grab items for list
        for(i in currCat.getItems().indices) {
            if (currCat.getItems().size > 0 && currCat.getItems().get(0) != null) {
                val cb: CheckBox = CheckBox(activity)
                val ll = fragmentView.findViewById<LinearLayout>(R.id.linearLayout)
                cb.text = currCat.getItems().get(i)
                ll.addView(cb)
                Log.i("Index size", currCat.getItems().size.toString())
                cb.setOnClickListener {
                    (activity as MainActivity).removeItem(currCat.getItems().get(i))
                    Toast.makeText((activity), "Item Removed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        return fragmentView
    }

    companion object {
        fun newInstance() : Fragment_main {
            return Fragment_main()
        }
    }

}