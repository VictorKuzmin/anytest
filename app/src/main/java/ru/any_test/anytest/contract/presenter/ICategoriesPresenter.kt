package ru.any_test.anytest.contract.presenter

import androidx.navigation.NavController

interface ICategoriesPresenter {

    fun onPresenterCreated(position: Int)

    fun onDataRequired()

    fun onItemWasClicked(position: Int, navController: NavController)
}