package ru.any_test.anytest.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.Single
import ru.any_test.anytest.UIThread
import ru.any_test.anytest.testlist.TestsAdapter
import ru.any_test.anytest.contract.presenter.ICategoriesPresenter
import ru.any_test.anytest.contract.repository.ICategoriesRepository
import ru.any_test.anytest.testlist.TestsPresenterImpl
import ru.any_test.anytest.di.scopes.ContractScope
import ru.any_test.anytest.domain.executor.JobExecutor
import ru.any_test.anytest.domain.usecases.LoadCategoriesUseCase
import ru.any_test.anytest.model.Category
import ru.any_test.anytest.utils.Images
import ru.any_test.anytest.testlist.FragmentTestsList
import javax.inject.Named

@Module
class TestsModule(val fragmentTestsList: FragmentTestsList) {

    @Named("testsAdapter")
    @Provides
    fun provideTestsAdapter(images: Images): TestsAdapter =
        TestsAdapter(images)

    @ContractScope
    @Named("testsPresenter")
    @Provides
    fun provideTestsPresenter(
        loadCategoriesUseCase: LoadCategoriesUseCase,
    ): ICategoriesPresenter {
        return TestsPresenterImpl(fragmentTestsList, loadCategoriesUseCase)
    }

    @ContractScope
    @Provides
    fun provideLoadingCategoriesUseCase(
        @Named("testListRepository") testsRepository: ICategoriesRepository<Single<List<Category>>>
    ): LoadCategoriesUseCase {
        return LoadCategoriesUseCase(
            JobExecutor(),
            UIThread(),
            testsRepository
        )
    }
}