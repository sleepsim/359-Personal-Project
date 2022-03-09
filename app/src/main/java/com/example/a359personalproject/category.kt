package com.example.a359personalproject

class category (categoryName: String){

    private var items:ArrayList<String> = ArrayList<String>()
    val name = categoryName

    public fun addItem(item:String){
        val text = item
        items.add(text)
    }

    public fun removeItem(item:String){
        val text = item
        val indexNum = items.indexOf(text)
        items.removeAt(indexNum)
    }

}