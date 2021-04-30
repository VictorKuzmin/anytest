package ru.any_test.anytest.contract.view

import ru.any_test.anytest.model.Category

interface IResultView {

    fun showCategories(categories: List<Category>)
}