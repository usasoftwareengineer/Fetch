package com.example.myapplication.repository

import com.example.myapplication.data.model.Data

/**
 * Represents the state of a resource and holds data and exception happens during network or storage fetch
 */
sealed class Resource<T : Data>(
    open val data: T?,
    open val e: Exception?,
    open val status: Status
) {

    data class Success<T : Data>(override val data: T) : Resource<T>(data, null, Status.SUCCESS)
    data class Failure<T : Data>(override val e: Exception) : Resource<T>(null, e, Status.FAILURE)
    data class Loading<T : Data>(override val data: T?) : Resource<T>(data, null, Status.LOADING)

    /**
     * IDLE represents the initial state of the resource when no operation is being performed.
     */
    enum class Status {
        IDLE, SUCCESS, FAILURE, LOADING
    }
}