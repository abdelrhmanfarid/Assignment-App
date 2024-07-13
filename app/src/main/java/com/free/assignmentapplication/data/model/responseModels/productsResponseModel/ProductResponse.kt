package com.free.assignmentapplication.data.model.responseModels.productsResponseModel

import com.free.assignmentapplication.data.local.room.Product
import com.free.assignmentapplication.data.model.responseModels.BaseResponseModel

data class ProductResponse(
    val id: Int,
    val title: String,
    val price: Double,
    val description: String,
    val category: Category,
    val images: List<String>
):BaseResponseModel(){
    fun toProduct(): Product {
        return Product(
            id = id,
            title = title,
            description = description,
            price = price,
            image = category.image
        )
    }
}
