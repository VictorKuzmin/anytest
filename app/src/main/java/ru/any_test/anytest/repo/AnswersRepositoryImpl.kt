package ru.any_test.anytest.repo

import ru.any_test.anytest.contract.OnCategoriesFetchedListener
import ru.any_test.anytest.contract.repository.IResultRepository
import ru.any_test.anytest.model.Category

class AnswersRepositoryImpl : IResultRepository {

    var answersForTest: List<Category> = ArrayList()

    lateinit var onQuestionsFetchedListener: OnCategoriesFetchedListener

    override fun getCategories(result: List<Category>) {
        answersForTest = result
        onQuestionsFetchedListener.showCategories(answersForTest)
    }

    override fun attach(onCategoriesFetchedListener: OnCategoriesFetchedListener) {
        this.onQuestionsFetchedListener = onCategoriesFetchedListener
    }

    override fun getCategories(): List<Category> {
        onQuestionsFetchedListener.showCategories(answersForTest)
        return answersForTest
    }
}