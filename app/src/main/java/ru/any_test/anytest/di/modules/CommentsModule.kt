package ru.any_test.anytest.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.Single
import ru.any_test.anytest.UIThread
import ru.any_test.anytest.comments.CommentsAdapter
import ru.any_test.anytest.comments.CommentsPresenterImpl
import ru.any_test.anytest.comments.FragmentComments
import ru.any_test.anytest.contract.repository.ICategoriesRepository
import ru.any_test.anytest.contract.view.ICategoryListView
import ru.any_test.anytest.contract.presenter.ICurrentTestPresenter
import ru.any_test.anytest.di.scopes.ContractScope
import ru.any_test.anytest.domain.executor.JobExecutor
import ru.any_test.anytest.domain.usecases.LoadTestUseCase
import ru.any_test.anytest.utils.Images
import ru.any_test.anytest.question.FragmentQuestion
import ru.any_test.anytest.repo.responses.TestResponse
import ru.any_test.anytest.root.MainActivity
import javax.inject.Named

@Module
class CommentsModule(val mainActivity: MainActivity, val fragmentComments: FragmentComments) {

    @ContractScope
    @Named("fragmentTest")
    @Provides
    fun provideFragmentTest(): ICategoryListView = FragmentQuestion.newInstance()

    @Named("commentsAdapter")
    @Provides
    fun provideCommentsAdapter(): CommentsAdapter = CommentsAdapter()

    @ContractScope
    @Named("commentsPresenter")
    @Provides
    fun provideCommentsPresenter(
        loadTestUseCase: LoadTestUseCase
    ): ICurrentTestPresenter {
        return CommentsPresenterImpl(fragmentComments, loadTestUseCase)
    }

    @ContractScope
    @Provides
    fun provideLoadingTestUseCase(
        @Named("testRepository") commentsRepository: ICategoriesRepository<Single<TestResponse>>
    ): LoadTestUseCase {
        return LoadTestUseCase(
            JobExecutor(),
            UIThread(),
            commentsRepository
        )
    }
}