package ru.any_test.anytest.testlist

import androidx.navigation.NavController
import io.reactivex.observers.DisposableSingleObserver
import ru.any_test.anytest.contract.presenter.ICategoriesPresenter
import ru.any_test.anytest.contract.view.ICategoryListView
import ru.any_test.anytest.domain.usecases.LoadCategoriesUseCase
import ru.any_test.anytest.model.Category

class TestsPresenterImpl(
    private val fragmentTestsList: ICategoryListView,
    private val loadContentUseCase: LoadCategoriesUseCase,
) : ICategoriesPresenter {

    lateinit var tests: List<Category>
    private var categoryId: Int = -1

    override fun onPresenterCreated(position: Int) {
        fragmentTestsList.showProgress()
        loadContentUseCase.setParam(position)
        loadContentUseCase.execute(TestsObserver())
    }

    override fun onDataRequired() {
        TODO("Not yet implemented: Под пагинацию.")
    }

    /**
     * По номеру позиции берем id теста. Чтобы по этому id уже найти для него пул вопросов.
     */
    override fun onItemWasClicked(
        position: Int,
        navController: NavController,
    ) {
        val id = (tests.get(position) as Category.Test).id
        val title = tests.get(position).name
        val action =
            FragmentTestsListDirections.actionFragmentTestsListToFragmentComments(title, id)
        navController.navigate(action)
    }

    private inner class TestsObserver : DisposableSingleObserver<List<Category>>() {
        override fun onSuccess(categories: List<Category>) {
            tests = categories
            fragmentTestsList.showCategories(categories)
            fragmentTestsList.hideProgress()
        }

        override fun onError(e: Throwable) {
            loadContentUseCase.setParam(categoryId)
            loadContentUseCase.execute(TestsObserver())
        }
    }
}