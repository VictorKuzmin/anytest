package ru.any_test.anytest.contract.presenter

import ru.any_test.anytest.model.Category

interface IResultPresenter {

    fun onPresenterCreated(list: List<Category>?)

    fun onConfirm(list: List<Category>)
}