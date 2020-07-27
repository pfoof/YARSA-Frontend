package com.furfel.yarsa_frontend.book_list

import androidx.lifecycle.MutableLiveData
import com.furfel.yarsa_frontend.CoroutineViewModel
import com.furfel.yarsa_frontend.models.BookModel
import java.lang.Exception
import com.furfel.yarsa_frontend.Result

class BookListViewModel(private val getBooks: GetBooks)
    : CoroutineViewModel() {

    val books: MutableLiveData<List<BookModel>> = MutableLiveData()
    val error: MutableLiveData<Exception> = MutableLiveData()

    fun requestBooks() {
        getBooks.execute(
            this, null,
            {books -> this.books.value = books},
            {exception -> this.error.value = exception.error })
    }

}