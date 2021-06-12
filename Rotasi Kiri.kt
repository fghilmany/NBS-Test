package org.example

fun main() {
    val array = arrayOf(1,2,3,4,5,6,7,8)
    val rotate = 5
    println("Before rotate: ${array.joinToString(", ")}")
    rotateLeft(array, rotate)
    println("After rotate: ${array.joinToString(", ")}")
}

private fun rotateLeft(arrayInt: Array<Int>, numOfRotation: Int){
    for (i in 1..numOfRotation){
        val first = arrayInt[0]
        for (j in 1 until arrayInt.size ) {
            arrayInt[j - 1] = arrayInt[j]
        }
        arrayInt[arrayInt.size - 1] = first
    }

}