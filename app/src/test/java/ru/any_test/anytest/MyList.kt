package ru.any_test.anytest

//For tests
open class MyList : AbstractList<String>() {

    override val size: Int
        get() = 0

    override fun get(index: Int): String {
        return "test"
    }

    fun clear() {}

    fun add(string: String) {}
}