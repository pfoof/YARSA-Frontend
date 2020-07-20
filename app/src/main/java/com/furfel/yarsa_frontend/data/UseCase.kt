package com.furfel.yarsa_frontend.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.lang.Exception

abstract class UseCase<out T, in Params> where T: Any {

    abstract suspend fun run(params: Params): Result<T>

    fun execute(parentScope: CoroutineScope,
                params: Params,
                success: (T) -> Unit,
                error: (Exception) -> Unit = {}) {

        val job = parentScope.async(Dispatchers.IO) { run(params) }

        parentScope.launch {
            val result = job.await()
            if(result is Result.Error)
                error(result.exception)
            else if(result is Result.Success)
                success(result.data)
        }

    }

}