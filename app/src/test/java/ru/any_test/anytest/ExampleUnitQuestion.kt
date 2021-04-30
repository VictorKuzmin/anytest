package ru.any_test.anytest

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InOrder
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import ru.any_test.anytest.domain.executor.JobExecutor
import ru.any_test.anytest.domain.usecases.LoadCategoriesUseCase
import ru.any_test.anytest.model.Category

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitQuestion {

//    @Test
    @Throws(Exception::class)
    fun testCategoryFound() {
        val categoryRepository = Mockito.mock(TestRepository::class.java)

        //expected
        val categories: List<Category> = mutableListOf<Category>(
            Category.TestCategory(
                0,
                "Тесты охранников",
                null,
                "Тесты для охранников разных разрядов",
                10,
                null
            )
        )
        val categorySingle = Single.fromCallable {
            categories
        }

        Mockito.`when`(categoryRepository?.getCategories(0)).thenReturn(categorySingle)

        val loadingCategoryUseCase: LoadCategoriesUseCase = LoadCategoriesUseCase(
            JobExecutor(),
            TestThread(),
            categoryRepository!!
        )
        loadingCategoryUseCase.execute()

        Mockito.verify<Any>(categoryRepository.getCategories(0))
    }


//  Examples from: https://www.baeldung.com/mockito-verify

    @Test
    fun simpleInvocationOfMock() {
        var mockedList: List<String> = mock(MyList::class.java)
        mockedList.size
        verify(mockedList).size
    }

    @Test
    fun numberOfInteractionsWithMock() {
        var mockedList: List<String> = mock(MyList::class.java)
        mockedList.size
        verify(mockedList, times(1))  //Любое значение можно подставить и будет правильно.
    }

    @Test
    fun noInteractionsWithWholeMockOccurred() {
        var mockedList: List<String> = mock(MyList::class.java)
//        mockedList.size  //Если сюда это добавить, то тест не пройдет (было взаимодействие)
        com.nhaarman.mockitokotlin2.verifyZeroInteractions(mockedList)
    }

    @Test
    fun noInteractionWithWithSpecificMethodOccured() {
        var mockedList: List<String> = mock(MyList::class.java)
//        mockedList.size  //Если сюда это добавить, то тест не пройдет (было взаимодействие с size)
        verify(mockedList, times(0)).size

//        mockedList.get(0)  //Если подставить 0, не пройдет, другую цифру в аргумент - пройдет
        verify(mockedList, times(0)).get(0)
    }

    @Test
    fun noUnexpectedInteractions () {
        var mockedList = mock(MyList::class.java)
        mockedList.size
        //mockedList.clear()  //Если это добавить...
        verify(mockedList).size
        verifyNoMoreInteractions(mockedList)   //...то это не пройдет
    }

    @Test
    fun orderOfInteractions() {
        var mockedList = mock(MyList::class.java)
        //Если мы, допустим, поменяем местами mockedList.add и mockedList.size, то тест не пройдет...
        mockedList.size
        mockedList.add("a parameter")
        mockedList.clear()
        var inOrder: InOrder = inOrder(mockedList)
        //...потому что такой порядок у нас задан:
        inOrder.verify(mockedList).size
        inOrder.verify(mockedList).add("a parameter")
        inOrder.verify(mockedList).clear()
    }


}
