package ru.any_test.anytest.contract.view

import ru.any_test.anytest.model.Category

interface ICategoryListView {

    fun showCategories(categories: List<Category>)

    fun showProgress()

    fun hideProgress()

    fun updateCategories(categories: List<Category>)
}