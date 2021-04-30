package ru.any_test.anytest.contract.repository

import ru.any_test.anytest.contract.OnCategoriesFetchedListener
import ru.any_test.anytest.model.Category

interface IResultRepository {
    fun getCategories(result: List<Category>)

    fun attach(onCategoriesFetchedListener: OnCategoriesFetchedListener)

    fun getCategories(): List<Category>
}