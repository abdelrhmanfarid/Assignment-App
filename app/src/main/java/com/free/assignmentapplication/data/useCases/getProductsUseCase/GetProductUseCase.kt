package com.free.assignmentapplication.data.useCases.getProductsUseCase

import com.free.assignmentapplication.data.BaseUseCase
import com.free.assignmentapplication.data.model.responseModels.productsResponseModel.ProductResponse
import com.free.assignmentapplication.data.remote.DataRepository
import javax.inject.Inject

class GetProductUseCase @Inject constructor(dataRepository: DataRepository) :
    BaseUseCase<List<ProductResponse>>(dataRepository) {
    override suspend fun executeOnBackground(
        map: MutableMap<String, Any>,
        headerMap: Map<String, Any>?
    ): List<ProductResponse> {
        return dataRepository.getProducts()
    }
}