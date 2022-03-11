package com.example.a359personalproject

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.CheckBox
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private var categories:ArrayList<ListCategory> = ArrayList<ListCategory>()
    private var categoryNames:ArrayList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categories.add(ListCategory("Main"))
        categories.add(ListCategory("Trash"))

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

        val addNewCatButton = findViewById<FloatingActionButton>(R.id.addCategoryButton)
        addNewCatButton.setOnClickListener {
            categories.add(ListCategory("newly added"))
            clearAdapter()
            initializeApp()
            for (f in categoryNames){
                Log.i("CATNAMES", f.toString())
            }
            Log.i("endOfArr", "END")
        }

        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val text1 = spinner.selectedItem.toString()
                Log.i("SPINNER", text1)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

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

    public fun getCat(): ArrayList<ListCategory>{
        return categories
    }

    private fun saveApp(){

    }

    fun addNew(){

    }
}