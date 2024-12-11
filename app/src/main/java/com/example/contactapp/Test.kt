package com.example.contactapp

fun main() {

    val test = "vishal Kumawat"
    val test1 = test.split(" ", ignoreCase = false)
   val test3 =  test1.filter {
        it.isNotBlank()
    }
   for (i in test){
       println()
   }

}