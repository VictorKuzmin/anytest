package ru.any_test.anytest.categories

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_categories.*
import kotlinx.android.synthetic.main.fragment_categories.view.*
import ru.any_test.anytest.App
import ru.any_test.anytest.R
import ru.any_test.anytest.contract.presenter.ICategoriesPresenter
import ru.any_test.anytest.contract.view.ICategoryListView
import ru.any_test.anytest.model.Category
import ru.any_test.anytest.utils.EndlessRecyclerViewScrollListener
import javax.inject.Inject
import javax.inject.Named

class FragmentCategoriesList : Fragment(), ICategoryListView {

    @field:[Inject Named("categoriesAdapter")]
    lateinit var categoriesAdapter: CategoriesAdapter

    @field:[Inject Named("categoriesPresenter")]
    lateinit var presenter: ICategoriesPresenter

    private var listState: Parcelable? = null
    private var progressBar: View? = null
    lateinit var categories: List<Category>

    override fun showCategories(categories: List<Category>) {
        this.categories = categories
        categoriesAdapter.setCategories(this.categories)
        categoriesAdapter.setOnItemClickListener(object : CategoriesAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                presenter.onItemWasClicked(position, findNavController())
            }
        })
        categoriesAdapter.notifyDataSetChanged()
        recyclerViewCategories.layoutManager?.onRestoreInstanceState(listState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.instance
            ?.initCategoriesComponent(this)
            ?.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categories, null)

        progressBar =
            inflater.inflate(R.layout.progress, view.categoriesListContainer, false)
        view.categoriesListContainer?.addView(progressBar)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        presenter.onPresenterCreated(position = POSITION_NOT_REQUIRED)

        recyclerViewCategories.setHasFixedSize(true)
        recyclerViewCategories.setAdapter(categoriesAdapter)
        val layoutManager = LinearLayoutManager(context)
        recyclerViewCategories.setLayoutManager(layoutManager)
        val endlessScrollListener = object: EndlessRecyclerViewScrollListener(layoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView?) {
                presenter.onDataRequired()
            }
        }
        recyclerViewCategories.addOnScrollListener(endlessScrollListener)
        if (savedInstanceState != null) {
            listState = savedInstanceState.getParcelable(LAYOUT_STATE)
        }
    }

    override fun updateCategories(categories: List<Category>) {
        this.categories = categories
        categoriesAdapter.setCategories(this.categories)
        categoriesAdapter.notifyDataSetChanged()
    }

    override fun showProgress() {
        progressBar?.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        progressBar?.visibility = View.GONE
    }

    override fun toString(): String {
        return "Категории"
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        listState = recyclerViewCategories.layoutManager?.onSaveInstanceState()
        outState.putParcelable(LAYOUT_STATE, listState)
    }

    companion object {
        fun newInstance(): FragmentCategoriesList {
            val fragmentCategoriesList = FragmentCategoriesList()

            return fragmentCategoriesList
        }
        const val LAYOUT_STATE = "layoutState"
        const val POSITION_NOT_REQUIRED = 0
    }
}