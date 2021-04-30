package ru.any_test.anytest.contract.repository

import ru.any_test.anytest.contract.OnCategoriesFetchedListener
import ru.any_test.anytest.model.Category

interface ICategoriesRepository<T> {

    fun getCategories(position: Int) : T

    fun attach(onCategoriesFetchedListener: OnCategoriesFetchedListener)

    fun getCategories(): List<Category>
}