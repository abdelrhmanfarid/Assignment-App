package com.free.assignmentapplication.ui.navigation.homeFragment

import com.free.assignmentapplication.data.local.room.Product
import com.free.assignmentapplication.data.local.room.ProductDao

class ProductRepository (private val productDao: ProductDao) {
    suspend fun getAllProducts(): List<Product> {
        return productDao.getAllProducts()
    }
}