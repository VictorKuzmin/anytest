package ru.any_test.anytest.question

import android.util.Log
import androidx.navigation.NavController
import io.reactivex.observers.DisposableSingleObserver
import ru.any_test.anytest.repo.TestRepositoryImpl
import ru.any_test.anytest.contract.presenter.ICurrentTestPresenter
import ru.any_test.anytest.contract.view.ICategoryListView
import ru.any_test.anytest.domain.usecases.LoadTestUseCase
import ru.any_test.anytest.model.Category
import ru.any_test.anytest.repo.CurrentTestRepository
import ru.any_test.anytest.utils.Constants

class QuestionsPresenterImpl(
    private val fragmentTest: ICategoryListView,
    private val loadTestUseCase: LoadTestUseCase,
    private val currentTestRepository: CurrentTestRepository
) : ICurrentTestPresenter {

    override fun onPresenterCreated(position: Int) {
        if(position <= 0){
            loadTestUseCase.setParam(TestRepositoryImpl.QUESTIONS_LOADED)
            loadTestUseCase.execute(QuestionObserver())
        } else {
            fragmentTest.showCategories(currentTestRepository.testQuestion)
        }
    }

    override fun onConfirm(
        navController: NavController,
        position: Int,
        list: List<String>?
    ) {
        var currentPosition = position
        val currentCategories = currentTestRepository.testQuestion
        if(position < currentCategories.size - 1) {
            currentCategories.get(position).userData = list
            currentPosition++
            (fragmentTest as FragmentQuestion).nextQuestion(currentPosition)
        } else {
            currentCategories.get(position).userData = list
            val action =
                FragmentQuestionDirections.actionFragmentQuestionToFragmentAnswerList(
                    currentCategories.toTypedArray()
                )
            navController.navigate(action)
        }
    }

    private inner class QuestionObserver : DisposableSingleObserver<List<Category>>() {

        override fun onSuccess(categories: List<Category>) {
            currentTestRepository.testQuestion = categories
            fragmentTest.showCategories(categories)
        }

        override fun onError(e: Throwable) {
            Log.d(Constants.APP_TAG, "QuestionObserver onError")
        }
    }
}