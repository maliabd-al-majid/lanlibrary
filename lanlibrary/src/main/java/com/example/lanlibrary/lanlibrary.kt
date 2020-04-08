package com.example.lanlibrary

data class twonumbers(var x:Int,var y:Int)
class calculation{
    fun calculate(twonumbers: twonumbers):Int{
        return twonumbers.x+ twonumbers.y
    }
}