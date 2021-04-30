package ru.any_test.anytest.answers

import ru.any_test.anytest.contract.OnCategoriesFetchedListener
import ru.any_test.anytest.contract.presenter.IResultPresenter
import ru.any_test.anytest.contract.repository.IResultRepository
import ru.any_test.anytest.contract.view.IResultView
import ru.any_test.anytest.model.Category

class AnswersPresenterImpl(
    private val fragmentAnswerList: IResultView,
    private val answersRepository: IResultRepository
) : IResultPresenter, OnCategoriesFetchedListener {

    init {
        answersRepository.attach(this)
    }

    override fun onPresenterCreated(list: List<Category>?) {
        if(list == null) {
            answersRepository.getCategories()
        }
        else {
            answersRepository.getCategories(list)
        }
    }

    override fun showCategories(categories: List<Category>) {
        fragmentAnswerList.showCategories(categories)
    }

    override fun error(message: String) {
        TODO("Not yet implemented")
    }

    override fun onConfirm(list: List<Category>) {
        TODO("Not yet implemented")
    }
}