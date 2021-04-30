package ru.any_test.anytest.testlist

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_tests_list.*
import kotlinx.android.synthetic.main.fragment_tests_list.view.*
import ru.any_test.anytest.App
import ru.any_test.anytest.R
import ru.any_test.anytest.contract.presenter.ICategoriesPresenter
import ru.any_test.anytest.contract.view.ICategoryListView
import ru.any_test.anytest.model.Category
import javax.inject.Inject
import javax.inject.Named

class FragmentTestsList : Fragment(), ICategoryListView {

    private val args: FragmentTestsListArgs by navArgs()

    var progressBar: View? = null

    @field:[Inject Named("testsAdapter")]
    lateinit var testsAdapter: TestsAdapter

    @field:[Inject Named("testsPresenter")]
    lateinit var presenter: ICategoriesPresenter

    private var listState: Parcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance?.initTestsComponent(this)?.inject(this)
    }

    override fun showCategories(categories: List<Category>) {
        testsAdapter.setCategories(categories)
        testsAdapter.setOnItemClickListener(object: TestsAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                presenter.onItemWasClicked(position, findNavController())
            }
        })
        testsAdapter.notifyDataSetChanged()
        recyclerViewTests.layoutManager?.onRestoreInstanceState(listState)
    }

    override fun updateCategories(categories: List<Category>) {
        TODO("Not yet implemented: Под пагинацию")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tests_list, container, false)

        progressBar = inflater.inflate(R.layout.progress, view.testListContainer, false)
        view.testListContainer?.addView(progressBar)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(LAYOUT_STATE)
        }

        presenter.onPresenterCreated(args.position)

        recyclerViewTests.setHasFixedSize(true)
        recyclerViewTests.setLayoutManager(LinearLayoutManager(context))
        recyclerViewTests.setAdapter(testsAdapter)
    }

    override fun showProgress() {
        progressBar?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar?.visibility = View.GONE
    }

    override fun toString(): String {
        return "Тесты"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(recyclerViewTests != null) {
            listState = recyclerViewTests.layoutManager?.onSaveInstanceState()
            outState.putParcelable(LAYOUT_STATE, listState)
        }
    }

    companion object {
        const val LAYOUT_STATE = "layoutState"
    }
}