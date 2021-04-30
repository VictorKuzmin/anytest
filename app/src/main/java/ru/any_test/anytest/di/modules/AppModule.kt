package ru.any_test.anytest.di.modules

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.Module
import dagger.Provides
import ru.any_test.anytest.App
import ru.any_test.anytest.utils.Images
import ru.any_test.anytest.root.FragmentPager
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule(val app : App) {

    @Provides
    fun provideContext(): Context = app

    @Singleton
    @Provides
    fun provideImages(context: Context): Images {
        return Images(context)
    }

    @Named("fragmentPager")
    @Provides
    fun provideFragmentPager(): Fragment = FragmentPager.newInstance()
}