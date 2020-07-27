package com.furfel.yarsa_frontend

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

/**
 * Created by stasbar on 19/06/2018
 */
abstract class UseCase<Type, in Params> where Type : Any {

    abstract suspend fun run(params: Params): Result<Type>

    fun execute(
        parentScope: CoroutineScope,
        params: Params,
        onSuccess: (Type) -> Unit,
        onFailure: (Result.Error<Type>) -> Unit = {}
    ) {
        val job = parentScope.async(Dispatchers.IO) { run(params) }
        parentScope.launch {
            val result = job.await()

            if(result is Result.Error<Type>)
                onFailure.invoke(result)
            else if(result is Result.Success<Type>)
                onSuccess.invoke(result.data)
        }
    }
}