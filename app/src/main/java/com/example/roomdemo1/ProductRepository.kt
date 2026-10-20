package com.example.roomdemo1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProductRepository(private val productDao: ProductDao) {

    // Список всех продуктов (LiveData автоматически обновит UI при изменении БД)
    val allProducts: LiveData<List<Product>> = productDao.getAllProducts()

    // Результаты поиска
    val searchResults = MutableLiveData<List<Product>>()

    // Корутины для выполнения операций в фоне (чтобы не вешать главный поток)
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun insertProduct(newProduct: Product) {
        coroutineScope.launch(Dispatchers.IO) {
            productDao.insertProduct(newProduct)
        }
    }

    fun deleteProduct(name: String) {
        coroutineScope.launch(Dispatchers.IO) {
            productDao.deleteProduct(name)
        }
    }

    fun findProduct(name: String) {
        coroutineScope.launch(Dispatchers.Main) {
            searchResults.value = asyncFind(name).await()
        }
    }

    private fun asyncFind(name: String) = coroutineScope.async(Dispatchers.IO) {
        productDao.findProduct(name)
    }
}