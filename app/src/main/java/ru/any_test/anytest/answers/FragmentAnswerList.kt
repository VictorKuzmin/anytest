package ru.any_test.anytest.answers

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_answers.view.*
import ru.any_test.anytest.App
import ru.any_test.anytest.R
import ru.any_test.anytest.contract.presenter.IResultPresenter
import ru.any_test.anytest.contract.view.IResultView
import ru.any_test.anytest.model.Category
import javax.inject.Inject
import javax.inject.Named

class FragmentAnswerList : Fragment(), IResultView {

    private val args: FragmentAnswerListArgs by navArgs()

    lateinit var recyclerViewAnswers: RecyclerView

    @field:[Inject Named("answersAdapter")]
    lateinit var answersAdapter: AnswersAdapter

    @field:[Inject Named("answersPresenter")]
    lateinit var presenter: IResultPresenter

    override fun showCategories(categories: List<Category>) {
        answersAdapter.setCategories(categories)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance?.initAnswersComponent(this)?.inject(this)

        presenter.onPresenterCreated(args.answerList.toList())
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_answers, container, false)

        recyclerViewAnswers = view.recyclerViewAnswers
        recyclerViewAnswers.setHasFixedSize(true)
        recyclerViewAnswers.setLayoutManager(LinearLayoutManager(context))

        recyclerViewAnswers.setAdapter(answersAdapter)

        return view
    }

    override fun toString(): String {
        return requireActivity().getString(R.string.categories)
    }

    companion object {
        fun newInstance(): FragmentAnswerList {
            val mainFragment = FragmentAnswerList()

            return mainFragment
        }
    }
}