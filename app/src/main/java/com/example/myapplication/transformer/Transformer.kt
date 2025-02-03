package com.example.myapplication.transformer

import com.example.myapplication.data.model.Data
import com.example.myapplication.repository.Resource
import com.example.myapplication.data.viewdata.ViewData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

/**
 * Transforms network or storage data into view data.
 * This is to keep network or storage data untouched.
 */
abstract class Transformer<INPUT: Data, OUTPUT: ViewData> {

    abstract fun transform(input: Flow<Resource<INPUT>>, scope: CoroutineScope): StateFlow<OUTPUT>
}