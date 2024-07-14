package com.free.assignmentapplication.ui.navigation.homeFragment

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.free.assignmentapplication.base.BaseViewModel
import com.free.assignmentapplication.data.local.LocalRepositoryImplementation
import com.free.assignmentapplication.data.local.room.AppDatabase
import com.free.assignmentapplication.data.local.room.Product
import com.free.assignmentapplication.data.model.responseModels.productsResponseModel.ProductResponse
import com.free.assignmentapplication.data.remote.ApiService
import com.free.assignmentapplication.data.useCases.getProductsUseCase.GetProductUseCase
import com.free.assignmentapplication.paging.ProductPagingSource
import com.free.assignmentapplication.utils.LiveDataResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProductUseCase: GetProductUseCase,
    private val database: AppDatabase,
    private val mainRepository: LocalRepositoryImplementation,
) : BaseViewModel() {

    private val productDao = database.productDao()


    private var _productLiveData =
        MutableStateFlow<LiveDataResource<List<ProductResponse>>>(LiveDataResource.EmptyState())
    val productLiveData: StateFlow<LiveDataResource<List<ProductResponse>>> get() = _productLiveData



    private val repository: ProductRepository
    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products
    init {
        val productDao = database.productDao()
        repository = ProductRepository(productDao)
    }

    fun getProducts() {
        _productLiveData.value = (LiveDataResource.Loading())
        getProductUseCase.execute({
            onComplete {
                _productLiveData.value = (LiveDataResource.Success(it))
            }
            onCancel {
                Log.d("get products task", "canceled")
            }
            onError {
                _productLiveData.value = (LiveDataResource.Exception())
            }
        }, params)

    }

    fun saveProductsToDatabase(productResponses: List<ProductResponse>) {
        val products = productResponses.map { it.toProduct() }
        viewModelScope.launch {
//            productDao.insertAll(products)

            for (item in products) {
                productDao.insertAll(item)
            }

        }
    }




    fun fetchAllProducts() {
        viewModelScope.launch {
            _products.value = repository.getAllProducts()
        }
    }


    override fun stop() {

        getProductUseCase.unsubscribe()
    }

    override fun start() {
    }


    fun emptyToken(){
        mainRepository.saveString("token","")
    }
}