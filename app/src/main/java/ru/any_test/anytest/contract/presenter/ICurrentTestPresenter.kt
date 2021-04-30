package ru.any_test.anytest.contract.presenter

import androidx.navigation.NavController

interface ICurrentTestPresenter {
    fun onPresenterCreated(position: Int)

    fun onConfirm(navController: NavController, position: Int, list: List<String>?)
}