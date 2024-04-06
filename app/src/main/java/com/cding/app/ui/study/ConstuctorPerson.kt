package com.cding.app.ui.study

/**
 *
 *  kotlin中执行对象顺序： 先执行按顺序init块，然后执行constructor()
 *  具体参照：https://juejin.cn/post/7353152184274386998
 */
class ConstuctorPerson {

   private var gender: Boolean = true

    companion object{
        val instance by lazy {
            ConstuctorPerson("zzzzzzz",false)
        }
//        val instance = ConstuctorPerson("zzzzzzz",false)


        /*伴生对象中的初始化代码*/
        init {
            println("-----------------Person companion init 1")
        }

        init {
            println("-----------------Person companion init 2")
        }

    }

    constructor(){
        println("-----------------Person constructor 1 ${this.gender}")
    }

    constructor(name: String, gender: Boolean): this(){
        this.gender = gender
        println("-----------------Person constructor 2 ${this.gender}")
    }

    init {
        println("-----------------Person init 1, gender:${gender}")
    }

    init {
        println("-----------------Person init 2")
    }

}