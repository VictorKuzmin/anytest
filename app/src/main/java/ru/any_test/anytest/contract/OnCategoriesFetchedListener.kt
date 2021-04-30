package ru.any_test.anytest.contract

import ru.any_test.anytest.model.Category
import java.io.Serializable

interface OnCategoriesFetchedListener : Serializable {

    fun showCategories(categories: List<Category>)

    fun error(message: String)
}