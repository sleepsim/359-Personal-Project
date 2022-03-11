package com.example.a359personalproject

class ListCategory (categoryName: String){

    private var items:ArrayList<String> = ArrayList<String>()
    val name: String = categoryName

    public fun addItem(item:String){
        val text = item
        items.add(text)
    }

    public fun removeItem(item:String){
        val text = item
        val indexNum = items.indexOf(text)
        items.removeAt(indexNum)
    }

    public fun getCategoryName(): String{
        return name
    }

    public fun getItems(): ArrayList<String>{
        return items
    }

    public fun removalTest(i: Int){
        items.removeAt(i)
    }

    public fun replaceItems(a: ArrayList<String>){
        items = a
    }

}