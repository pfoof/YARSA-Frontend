package com.furfel.yarsa_frontend.book_list

import com.furfel.yarsa_frontend.data.Result
import com.furfel.yarsa_frontend.data.UseCase
import com.furfel.yarsa_frontend.models.BookModel

class GetBooks(private val bookListDataSource: BookListDataSource)
    : UseCase<List<BookModel>, Void?>() {

    override suspend fun run(params: Void?): Result<List<BookModel>>
        = bookListDataSource.getBooks()
}