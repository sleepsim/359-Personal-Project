package com.example.a359personalproject

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    var categories:ArrayList<ListCategory> = ArrayList<ListCategory>()
    var categoryNames:ArrayList<String> = ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categories.add(ListCategory("Main"))
        categories.add(ListCategory("new"))

        if(savedInstanceState == null) {
            //initilize
            initiliazeApp()
            //Initial fragment loaded first open
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer,
                    Fragment_main.newInstance(),
                    "MyTag"
                ).commit()
        }
    }

    private fun initiliazeApp() {
        //Set up categories and drop down menu
        for (i in categories){
            categoryNames.add(i.getCategoryName())
        }

        val arrayAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_dropdown_item, categoryNames)
        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }

//        val spinner = findViewById<Spinner>(R.id.spinner)
//        categories.add(ListCategory("Main"))
//        val adapter = ArrayAdapter<ListCategory>(this, android.R.layout.simple_spinner_dropdown_item, categories)
//        spinner.adapter(adapter)

    }
}