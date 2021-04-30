package ru.any_test.anytest.contract.view

import ru.any_test.anytest.model.Category

interface ICommentView {

    fun showCategories(categories: List<Category>)

    fun addCategory(category: Category)
}