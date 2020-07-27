package com.furfel.yarsa_frontend.book_list

import com.furfel.yarsa_frontend.models.BookModel
import com.furfel.yarsa_frontend.Result
import java.lang.Exception
import java.net.URL

class BookListDataSource {

    fun getBooks(): Result<List<BookModel>> {
        try {
            val bookList = listOf<BookModel>()
            val url = URL("https://pabis.eu/books/")
            return Result.Success(bookList)
        } catch (e: Exception) {
            return Result.Error(e)
        }
    }

}