package org.example

fun main() {
    val array = arrayOf("a", "b", "c", "d", "e", "f" )
    val rotate = 1
    println("Before rotate: ${array.joinToString(", ")}")
    for (i in 1..rotate){
        leftRotate(array)
    }
    println("After rotate: ${array.joinToString(", ")}")
}

private fun leftRotate(array: Array<String>) {
    val first = array[0]
    for (i in 1 until array.size ) {
        array[i - 1] = array[i]
    }
    array[array.size - 1] = first
}