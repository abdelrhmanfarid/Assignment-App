package com.free.assignmentapplication.data.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(product: Product)

    @Query("SELECT * FROM products")
     suspend fun getAllProducts(): List<Product>
}