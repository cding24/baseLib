package com.cding.app.ui.study


class LazyDog {
    val person: ConstuctorPerson by lazy {
        ConstuctorPerson()
    }
}