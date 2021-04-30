package ru.any_test.anytest.comments

import android.util.Log
import androidx.navigation.NavController
import io.reactivex.observers.DisposableSingleObserver
import ru.any_test.anytest.contract.presenter.ICurrentTestPresenter
import ru.any_test.anytest.contract.view.ICategoryListView
import ru.any_test.anytest.contract.view.ICommentView
import ru.any_test.anytest.domain.usecases.LoadTestUseCase
import ru.any_test.anytest.model.Category
import ru.any_test.anytest.utils.Constants

class CommentsPresenterImpl(
    private val fragmentComments: ICommentView,
    private val loadTestUseCase: LoadTestUseCase
) : ICurrentTestPresenter {

    lateinit var comments: List<Category>

    override fun onPresenterCreated(position: Int) {
        loadTestUseCase.setParam(position)
        loadTestUseCase.execute(CommentsObserver())
    }

    override fun onConfirm(navController: NavController, position: Int, list: List<String>?) {
        when(position) {
            BUTTON_COMMENT -> {
                val newMessage = Category.Comment("Я", Category.TYPE_MY_MESSAGE, list)
                fragmentComments.addCategory(newMessage)
            }
            BUTTON_BEGIN_TEST -> {
                val action =
                    FragmentCommentsDirections.actionFragmentCommentsToFragmentQuestion()
                navController.navigate(action)
            }
        }
    }

    private inner class CommentsObserver : DisposableSingleObserver<List<Category>>() {
        override fun onSuccess(categories: List<Category>) {
            comments = categories
            fragmentComments.showCategories(categories)
        }

        override fun onError(e: Throwable) {
            Log.d(Constants.APP_TAG, "CommentsObserver onError")
        }
    }

    companion object {
        const val BUTTON_BEGIN_TEST = 0
        const val BUTTON_COMMENT = 1
    }
}