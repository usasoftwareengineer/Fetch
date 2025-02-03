package com.example.myapplication.repository

import com.example.myapplication.data.model.Data
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

/**
 * Default network repository implementation using OkHttp
 */
open class Repository {
    private val client: OkHttpClient = OkHttpClient.Builder().build()
    private val resourceStatus = MutableStateFlow(Resource.Status.IDLE)

    /**
     * wrap with a key to build model with Gson
     */
    open fun wrappedJson(json: String?): String? {
        return json
    }

    /**
     * Base get method for API call
     * 1. make an API call
     * 2. Based on the result, put it into Resource
     * 3. Return flow
     */
    fun <T : Data> get(url: String, className: Class<T>): Flow<Resource<T>> = flow {
        val request = Request.Builder().url(url).build()
        emit(Resource.Loading(null)) // Initial loading state
        resourceStatus.value = Resource.Status.LOADING

        try {
            val response = client.newCall(request).execute()
            if (response.isSuccessful) {
                val body = response.body?.string()
                if (body != null) {
                    val model = Gson().fromJson(wrappedJson(body), className)
                    emit(Resource.Success(model))
                    resourceStatus.value = Resource.Status.SUCCESS
                } else {
                    emit(Resource.Failure<T>(IOException("Response body is null")))
                    resourceStatus.value = Resource.Status.FAILURE
                }
            } else {
                emit(Resource.Failure<T>(IOException("HTTP error ${response.code}")))
                resourceStatus.value = Resource.Status.FAILURE
            }
        } catch (e: Exception) {
            emit(Resource.Failure<T>(e))
            resourceStatus.value = Resource.Status.FAILURE
        }
    }.flowOn(Dispatchers.IO) as Flow<Resource<T>>

    /**
     * Check if API call is in progress
     */
    fun isLoading(): Flow<Boolean> {
        return resourceStatus.map { it == Resource.Status.LOADING || it == Resource.Status.IDLE }
    }
}