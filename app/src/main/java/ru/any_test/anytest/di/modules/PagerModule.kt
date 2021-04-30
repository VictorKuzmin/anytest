package ru.any_test.anytest.di.modules

import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import ru.any_test.anytest.root.MainPagerAdapter
import ru.any_test.anytest.contract.view.ICategoryListView
import ru.any_test.anytest.di.scopes.ContractScope
import ru.any_test.anytest.root.FragmentBlog
import ru.any_test.anytest.categories.FragmentCategoriesList
import ru.any_test.anytest.root.MainActivity
import javax.inject.Named

@Module
class PagerModule(val mainActivity: MainActivity) {

    @ContractScope
    @Named("fragmentCategoriesList")
    @Provides
    fun provideFragmentCategories(): ICategoryListView = FragmentCategoriesList.newInstance()

    @ContractScope
    @Named("fragmentBlog")
    @Provides
    fun provideFragmentBlog(): Fragment = FragmentBlog.newInstance()

    @ContractScope
    @Provides
    fun providePagerAdapter(
        @Named("fragmentCategoriesList")fragmentCategories: ICategoryListView,
        @Named("fragmentBlog")fragmentBlog: Fragment
    ): MainPagerAdapter {
        val fragments = mutableListOf<Fragment>()
        fragments.add(fragmentCategories as FragmentCategoriesList)
        fragments.add(fragmentBlog)
        return MainPagerAdapter(mainActivity, fragments)
    }
}