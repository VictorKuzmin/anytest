package ru.any_test.anytest.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.Single
import ru.any_test.anytest.UIThread
import ru.any_test.anytest.categories.CategoriesAdapter
import ru.any_test.anytest.contract.presenter.ICategoriesPresenter
import ru.any_test.anytest.contract.repository.ICategoriesRepository
import ru.any_test.anytest.contract.view.ICategoryListView
import ru.any_test.anytest.categories.CategoriesPresenterImpl
import ru.any_test.anytest.di.scopes.ContractScope
import ru.any_test.anytest.utils.Images
import ru.any_test.anytest.categories.FragmentCategoriesList
import ru.any_test.anytest.domain.executor.JobExecutor
import ru.any_test.anytest.domain.usecases.LoadCategoriesUseCase
import ru.any_test.anytest.model.Category
import javax.inject.Named

@Module
class CategoriesModule (val fragmentCategoriesList: FragmentCategoriesList) {

    @Named("categoriesAdapter")
    @Provides
    fun provideCategoriesAdapter(images: Images): CategoriesAdapter = CategoriesAdapter(images)

    @ContractScope
    @Named("categoriesPresenter")
    @Provides
    fun provideCategoriesPresenter(
        loadCategoriesUseCase: LoadCategoriesUseCase
    ): ICategoriesPresenter {
        return CategoriesPresenterImpl(
            fragmentCategoriesList as ICategoryListView,
            loadCategoriesUseCase
        )
    }

    @ContractScope
    @Provides
    fun provideLoadingCategoriesUseCase(
        @Named("categoriesRepository") categoriesRepository: ICategoriesRepository<Single<List<Category>>>
    ): LoadCategoriesUseCase {
        return LoadCategoriesUseCase(
            JobExecutor(),
            UIThread(),
            categoriesRepository
        )
    }
}