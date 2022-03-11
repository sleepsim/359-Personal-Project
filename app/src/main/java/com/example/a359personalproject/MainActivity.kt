package com.example.a359personalproject

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private var categories:ArrayList<ListCategory> = ArrayList<ListCategory>()
    private var categoryNames:ArrayList<String> = ArrayList<String>()
    private var currentCategory: ListCategory = ListCategory("Filler")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        categories.add(ListCategory("Main"))
        categories.add(ListCategory("Trash"))
        currentCategory = categories.get(0)

        if(savedInstanceState == null) {
            //initialize
            initializeApp()
            //Initial fragment loaded first open
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer,
                    Fragment_main.newInstance(),
                    "FragmentMain"
                ).commit()
        }

        //Button to add new categories
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

        //Changes the data depending on selected spinner item
        val spinner = findViewById<Spinner>(R.id.spinner)
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                val currentCategoryText = spinner.selectedItem.toString()
                for(i in categories) {
                    if (i.getCategoryName() == currentCategoryText) {
                        setCurrentCategory(i)
                    }
                }
                Toast.makeText(applicationContext, "Category Changed", Toast.LENGTH_SHORT).show()
                redraw()
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

    public fun getCat(): ListCategory {
        return currentCategory
    }

    public fun setCurrentCategory(listItem: ListCategory){
        currentCategory = listItem
    }

    private fun saveApp(){

    }

    public fun redraw(){
        val frag = supportFragmentManager.findFragmentByTag("FragmentMain")
        supportFragmentManager
            .beginTransaction()
            .detach(supportFragmentManager.findFragmentByTag("FragmentMain")!!)
            .commit()
        supportFragmentManager
            .beginTransaction()
            .attach(supportFragmentManager.findFragmentByTag("FragmentMain")!!)
            .commit()
    }

    fun addNew(){

    }
}