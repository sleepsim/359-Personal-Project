package com.example.a359personalproject

import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private var categories:ArrayList<ListCategory> = ArrayList<ListCategory>()
    private var categoryNames:ArrayList<String> = ArrayList<String>()
    private var currentCategory: ListCategory = ListCategory("Filler")
    private var inText: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        loadCategoryData()
        loadCategoryItemsData()
        //First run
        if(savedInstanceState == null) {
            //Default category if no saved data
            if(categories.isEmpty()) categories.add(ListCategory("Main"))

            currentCategory = categories.get(0)
            //initialize
            clearAdapter()
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
            showInputBoxCat()
            clearAdapter()
            initializeApp()
            updateSpinner()
        }

        //Changes the data depending on selected spinner item
        updateSpinner()

        //Button to add new items on list
        val addNewItemButton = findViewById<FloatingActionButton>(R.id.addItemButton)
        addNewItemButton.setOnClickListener {
            showInputBox()
            redraw()
        }

        //Remove category button
        val removeCatButton = findViewById<FloatingActionButton>(R.id.removeCatButton)
        removeCatButton.setOnClickListener {
            var indexHolder = 999
            for (i in categories){
                if(currentCategory.getCategoryName() == i.getCategoryName()){
                    indexHolder = categories.indexOf(i)
                    break
                }
            }
            if(indexHolder != 999 && categories.get(indexHolder).getCategoryName() != "Main") categories.removeAt(indexHolder)

            clearAdapter()
            initializeApp()
            updateSpinner()
        }

    }

    //Save data when pausing or destroying app
    override fun onPause() {
        super.onPause()
        saveItemData()
        saveCategories()
    }

    override fun onDestroy() {
        super.onDestroy()
        saveItemData()
        saveCategories()
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

    }
    private fun clearAdapter(){
        val spinner = findViewById<Spinner>(R.id.spinner)
        val arrayAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_dropdown_item, categoryNames)
        spinner.adapter = arrayAdapter
        arrayAdapter.clear()
        arrayAdapter.notifyDataSetChanged()
        Log.i("Adapter ran", "i do work")
    }

    //Gets the current category to pass on to fragment
    public fun getCat(): ListCategory {
        return currentCategory
    }

    //Sets current category
    public fun setCurrentCategory(listItem: ListCategory){
        currentCategory = listItem
    }


    //Refreshes the fragment
    public fun redraw(){
        supportFragmentManager
            .beginTransaction()
            .detach(supportFragmentManager.findFragmentByTag("FragmentMain")!!)
            .commit()
        supportFragmentManager
            .beginTransaction()
            .attach(supportFragmentManager.findFragmentByTag("FragmentMain")!!)
            .commit()
    }

    //Removes an item from list
    public fun removeItem(s : String){
        //Remove the item
        currentCategory.removeItem(s)

        //Clone to original copy
        for (i in categories.indices){
            if(categories.get(i).getCategoryName() == currentCategory.getCategoryName()) {
                categories[i] = currentCategory
                break
            }
        }
        redraw()
    }

    //Add new items
    fun showInputBox(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Enter task name")

        val input = EditText(this)
        input.setHint("Enter text")
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
            inText = input.text.toString()
            currentCategory.addItem(inText)
            Toast.makeText(applicationContext, "Item added", Toast.LENGTH_SHORT).show()
            redraw()
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->

        })

        builder.show()
    }

    fun showInputBoxCat(){
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setTitle("Enter category name")

        val input = EditText(this)
        input.setHint("Enter text")
        input.inputType = InputType.TYPE_CLASS_TEXT
        builder.setView(input)

        builder.setPositiveButton("OK", DialogInterface.OnClickListener { dialogInterface, i ->
            inText = input.text.toString()
            categories.add(ListCategory(inText))
            Toast.makeText(applicationContext, "Category Added", Toast.LENGTH_SHORT).show()
            clearAdapter()
            initializeApp()
            updateSpinner()
        })
        builder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialogInterface, i ->

        })

        builder.show()
    }

    //Persistent data ---------------------------------------------------------------------------------------------------------------------------------------------

    //Save individual list items
    public fun saveItemData(){

        val sharedPreferences:SharedPreferences = this.getSharedPreferences("itemsList", Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        for(i in categories){
            var set = i.getItems().toHashSet()
            editor.putStringSet(i.getCategoryName(), set)
        }
        editor.apply()
    }

    //Save category names
    public fun saveCategories(){
        val sharedPreferences:SharedPreferences = this.getSharedPreferences("categoriesList", Context.MODE_PRIVATE)
        val editor:SharedPreferences.Editor = sharedPreferences.edit()
        var set = categoryNames.toHashSet()
        editor.putStringSet("Categories", set)
        for (j in set) {
            Log.i("Save test", j.toString())
        }
        editor.apply()
    }

    //Load category names
    private fun loadCategoryData(){
        //Load category names and add them into an array
        val sharedPreferences:SharedPreferences = this.getSharedPreferences("categoriesList", Context.MODE_PRIVATE)
        val savedString = sharedPreferences.getStringSet("Categories", null)
        if(savedString != null) {
            categoryNames = ArrayList(savedString)
            for (i in categoryNames) {
                categories.add(ListCategory(i))
                Log.i("datashared", i)
            }
        }
    }

    //Load individual item data
    private fun loadCategoryItemsData(){
        //Load the items of each category into an array and insert into correct category
        val sharedPreferences:SharedPreferences = this.getSharedPreferences("itemsList", Context.MODE_PRIVATE)
        Log.i("datashared", "Categories loaded")
        for(i in categoryNames){
            val savedItems = sharedPreferences.getStringSet(i, null)
            Log.i("datashared", "Categories loaded inside")
            if(savedItems != null){
                for(j in categories) if(j.getCategoryName() == i) {
                    j.replaceItems(ArrayList(savedItems))
                    break
                }
            }
        }
    }

    private fun updateSpinner(){
        var spinner = findViewById<Spinner>(R.id.spinner)
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                val currentCategoryText = spinner.selectedItem.toString()
                for(i in categories) {
                    if (i.getCategoryName() == currentCategoryText) {
                        setCurrentCategory(i)
                        Toast.makeText(applicationContext, "Category changed", Toast.LENGTH_SHORT).show()
                    }
                }
                redraw()
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

        }
        saveCategories()
    }
}