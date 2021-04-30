package ru.any_test.anytest.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.Single
import ru.any_test.anytest.UIThread
import ru.any_test.anytest.contract.repository.ICategoriesRepository
import ru.any_test.anytest.question.QuestionsPresenterImpl
import ru.any_test.anytest.di.scopes.ContractScope
import ru.any_test.anytest.answers.FragmentAnswerList
import ru.any_test.anytest.contract.view.IResultView
import ru.any_test.anytest.contract.presenter.ICurrentTestPresenter
import ru.any_test.anytest.domain.executor.JobExecutor
import ru.any_test.anytest.domain.usecases.LoadTestUseCase
import ru.any_test.anytest.question.FragmentQuestion
import ru.any_test.anytest.repo.CurrentTestRepository
import ru.any_test.anytest.repo.responses.TestResponse
import javax.inject.Named

@Module
class QuestionModule(val fragmentQuestion: FragmentQuestion) {

    @ContractScope
    @Named("fragmentAnswerList")
    @Provides
    fun provideFragmentAnswerList(): IResultView = FragmentAnswerList.newInstance()

    @ContractScope
    @Named("questionsPresenter")
    @Provides
    fun provideQuestionsPresenter(
        loadTestUseCase: LoadTestUseCase,
        currentTestRepository: CurrentTestRepository
    ): ICurrentTestPresenter {
        return QuestionsPresenterImpl(
            fragmentQuestion,
            loadTestUseCase,
            currentTestRepository
        )
    }

    @ContractScope
    @Provides
    fun provideLoadingCategoriesUseCase(
        @Named("testRepository") commentsRepository: ICategoriesRepository<Single<TestResponse>>
    ): LoadTestUseCase {
        return LoadTestUseCase(
            JobExecutor(),
            UIThread(),
            commentsRepository
        )
    }
}