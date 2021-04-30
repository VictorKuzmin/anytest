package ru.any_test.anytest.categories

import androidx.navigation.NavController
import io.reactivex.observers.DisposableSingleObserver
import ru.any_test.anytest.contract.presenter.ICategoriesPresenter
import ru.any_test.anytest.contract.view.ICategoryListView
import ru.any_test.anytest.model.Category
import ru.any_test.anytest.domain.usecases.LoadCategoriesUseCase
import ru.any_test.anytest.root.FragmentPagerDirections

class CategoriesPresenterImpl(
    private val fragmentCategories: ICategoryListView,
    private val loadContentUseCase: LoadCategoriesUseCase
) : ICategoriesPresenter {

    override fun onPresenterCreated(position: Int) {
        fragmentCategories.showProgress()
        loadContentUseCase.execute(CategoriesObserver(true))
    }

    override fun onDataRequired() {
        fragmentCategories.showProgress()
        loadContentUseCase.execute(CategoriesObserver(false))
    }

    override fun onItemWasClicked(
        position: Int,
        navController: NavController,
    ) {
        val action =
            FragmentPagerDirections.actionFragmentPagerToFragmentTestsList(position)

        navController.navigate(action)
    }

    private inner class CategoriesObserver(
        private val initialLoad: Boolean
    ) : DisposableSingleObserver<List<Category>>() {

        override fun onSuccess(categories: List<Category>) {
            if(initialLoad) {
                fragmentCategories.showCategories(categories)
                fragmentCategories.hideProgress()
            }
            else {
                fragmentCategories.updateCategories(categories)
                fragmentCategories.hideProgress()
            }
        }

        override fun onError(e: Throwable) {
            loadContentUseCase.execute(CategoriesObserver(initialLoad))
        }
    }
}