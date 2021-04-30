package ru.any_test.anytest.comments

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_comments.view.*
import ru.any_test.anytest.App
import ru.any_test.anytest.R
import ru.any_test.anytest.contract.view.ICommentView
import ru.any_test.anytest.contract.presenter.ICurrentTestPresenter
import ru.any_test.anytest.model.Category
import ru.any_test.anytest.root.MainActivity
import javax.inject.Inject
import javax.inject.Named

class FragmentComments : Fragment(), ICommentView {

    private val args: FragmentCommentsArgs by navArgs()

    lateinit var recyclerViewComments: RecyclerView
    lateinit var comments: List<Category>

    @field:[Inject Named("commentsAdapter")]
    lateinit var commentsAdapter: CommentsAdapter

    @field:[Inject Named("commentsPresenter")]
    lateinit var presenter: ICurrentTestPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance
            ?.initCommentsComponent(context as MainActivity, this)
            ?.inject(this)
    }

    override fun showCategories(categories: List<Category>) {
        comments = categories
        commentsAdapter.setCategories(comments)
        commentsAdapter.notifyDataSetChanged()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_comments, container, false)

        presenter.onPresenterCreated(args.testId)

        recyclerViewComments = view.recyclerViewComments
        recyclerViewComments.setHasFixedSize(true)
        recyclerViewComments.setLayoutManager(LinearLayoutManager(context))
        recyclerViewComments.setAdapter(commentsAdapter)
        view.textViewCurrentTest.text = args.testTitle

        setOnButtonsClickListeners(view)

        return view
    }

    private fun setOnButtonsClickListeners(view: View) {
        view.buttonMessageBoxSend.setOnClickListener {
            val message = view.editTextMessageBox.text.toString()
            if(!message.equals("")) {
                presenter.onConfirm(
                    findNavController(),
                    CommentsPresenterImpl.BUTTON_COMMENT,
                    arrayListOf(message)
                )
                view.editTextMessageBox.setText("")
            }
        }

        view.buttonStartTest.setOnClickListener {
            presenter.onConfirm(
                findNavController(),
                CommentsPresenterImpl.BUTTON_BEGIN_TEST,
                null
            )
        }
    }

    override fun addCategory(category: Category) {
        (comments as ArrayList).add(category)
        commentsAdapter.notifyItemInserted(comments.size - 1)
        recyclerViewComments.smoothScrollToPosition(commentsAdapter.getItemCount());
    }

    override fun toString(): String {
        return requireActivity().getString(R.string.comments)
    }
}