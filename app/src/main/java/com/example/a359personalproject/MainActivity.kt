package com.example.a359personalproject

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private var categories:ArrayList<ListCategory> = ArrayList<ListCategory>()
    private var categoryNames:ArrayList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categories.add(ListCategory("Main"))
        categories.add(ListCategory("new"))

        if(savedInstanceState == null) {
            //initialize
            initializeApp()
            //Initial fragment loaded first open
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer,
                    Fragment_main.newInstance(),
                    "MyTag"
                ).commit()
        }

        val addNewButton = findViewById<FloatingActionButton>(R.id.addButton)
        addNewButton.setOnClickListener {
            categories.add(ListCategory("newly added"))
            clearAdapter()
            initializeApp()
            for (f in categoryNames){
                Log.i("CATNAMES", f.toString())
            }
            Log.i("endOfArr", "END")
        }
    }

    //Resets the categories
    private fun initializeApp() {
        //Set up categories and drop down menu
        for (i in categories){
            categoryNames.add(i.getCategoryName())
        }

        val spinner = findViewById<Spinner>(R.id.spinner)
        val arrayAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_dropdown_item, categoryNames)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
    }
    private fun clearAdapter(){
        val spinner = findViewById<Spinner>(R.id.spinner)
        val arrayAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_dropdown_item, categoryNames)
        spinner.adapter = arrayAdapter
        arrayAdapter.clear()
        arrayAdapter.notifyDataSetChanged()
    }

    private fun saveApp(){

    }

    fun addNew(){

    }
}