package com.free.assignmentapplication.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.free.assignmentapplication.data.model.responseModels.productsResponseModel.ProductResponse
import com.free.assignmentapplication.data.remote.ApiService

class ProductPagingSource (
    private val apiService: ApiService,
    private val limit: Int
) : PagingSource<Int, ProductResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductResponse> {
        val offset = params.key ?: 0

        return try {
            val response = apiService.getProductsPaginated(offset, limit)
            val products = response.body() ?: emptyList()

            LoadResult.Page(
                data = products,
                prevKey = if (offset == 0) null else offset - limit,
                nextKey = if (products.isEmpty()) null else offset + limit
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ProductResponse>): Int? {
        return null
    }
}