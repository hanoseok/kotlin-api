package com.kakao.aston

import org.junit.jupiter.api.Test

class KotlinTest {

    class Inner {
        var a: Int? = null
        var l: List<Int>? = null
    }
    @Test
    public fun test(){

        var g = Inner()

        println(g.l.let{
            it.let{
                it?.first()
            }
        })
    }

}