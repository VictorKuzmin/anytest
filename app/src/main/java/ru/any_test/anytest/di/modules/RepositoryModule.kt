package ru.any_test.anytest.di.modules

import dagger.Module
import dagger.Provides
import io.reactivex.Single
import ru.any_test.anytest.repo.AnswersRepositoryImpl
import ru.any_test.anytest.repo.CategoriesRepositoryImpl
import ru.any_test.anytest.repo.TestRepositoryImpl
import ru.any_test.anytest.contract.repository.ICategoriesRepository
import ru.any_test.anytest.contract.repository.IResultRepository
import ru.any_test.anytest.model.Category
import ru.any_test.anytest.repo.CurrentTestRepository
import ru.any_test.anytest.repo.responses.TestResponse
import ru.any_test.anytest.repo.TestsRepositoryImpl
import javax.inject.Named
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Named("categoriesRepository")
    @Provides
    fun provideCategoriesRepository(): ICategoriesRepository<Single<List<Category>>> {
        return CategoriesRepositoryImpl()
    }

    @Singleton
    @Named("answersRepository")
    @Provides
    fun provideAnswersRepository(): IResultRepository {
        return AnswersRepositoryImpl()
    }

    @Singleton
    @Provides
    fun provideCurrentTestRepository(): CurrentTestRepository {
        return CurrentTestRepository()
    }

    @Singleton
    @Named("testListRepository")
    @Provides
    fun provideTestsRepository(): ICategoriesRepository<Single<List<Category>>> {
        return TestsRepositoryImpl()
    }

    @Singleton
    @Named("testRepository")
    @Provides
    fun provideCommentsRepository(): ICategoriesRepository<Single<TestResponse>> {
        return TestRepositoryImpl()
    }
}