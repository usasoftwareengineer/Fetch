package com.example.myapplication.repository

import com.example.myapplication.data.model.ItemList
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * This is a repository class for fetching items from a remote data source.
 */
@ViewModelScoped
class FetchRepository @Inject constructor() : Repository() {
    companion object {
        // Put base url just for convenience
        private const val BASE_URL = "https://fetch-hiring.s3.amazonaws.com/hiring.json"
    }

    fun fetchItemList(): Flow<Resource<ItemList>> {
        return get(BASE_URL, ItemList::class.java)
    }

    override fun wrappedJson(json: String?): String? {
        return json?.let { "{\"items\":$json}" }
    }
}