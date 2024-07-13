package com.free.assignmentapplication.ui.navigation.homeFragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.free.assignmentapplication.data.local.room.Product
import com.free.assignmentapplication.data.model.responseModels.productsResponseModel.ProductResponse
import com.free.assignmentapplication.databinding.FragmentHomeBinding
import com.free.assignmentapplication.ui.navigation.homeFragment.adapter.ProductsOfflineRecyclerAdapter
import com.free.assignmentapplication.ui.navigation.homeFragment.adapter.ProductsRecyclerAdapter
import com.free.assignmentapplication.ui.navigation.loginFragment.LoginFragmentDirections
import com.free.assignmentapplication.utils.LiveDataResource
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {


    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        homeViewModel.getProducts()
        observeGetProducts()

        homeViewModel.products.observe(viewLifecycleOwner, Observer { products ->
            Log.d("test_offline",""+products)
            initProductsRecyclerInOfflineMode(products,binding.productsRecyclerView)
        })



    }


    private fun observeGetProducts() {
        lifecycleScope.launchWhenStarted {
            homeViewModel.productLiveData.collect {
                when (it) {
                    is LiveDataResource.Success -> {

                        binding.progressBar.visibility = View.GONE
                        homeViewModel.saveProductsToDatabase(it.data!!)

                        initProductsRecycler(it.data!!, binding.productsRecyclerView)

                    }

                    is LiveDataResource.ErrorResponse -> {
                        Log.d("ErrorResponse", it.message.toString())

                        binding.progressBar.visibility = View.GONE
                    }

                    is LiveDataResource.Exception -> {
                        Log.d("Exception", "Exception")
                        binding.progressBar.visibility = View.GONE

                    }

                    is LiveDataResource.Loading -> {
                        Log.d("products Loading", "Loading")
                        binding.progressBar.visibility = View.VISIBLE


                    }

                    is LiveDataResource.NoNetwork -> {
                        Log.d("NoNetwork", "NoNetwork")
                        homeViewModel.fetchAllProducts()


                    }

                    else -> {

                    }
                }
            }
        }
    }




    private fun initProductsRecycler(
        productsList: List<ProductResponse>,
        recyclerView: RecyclerView,
    ) {

        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        val productsRecyclerAdapter = ProductsRecyclerAdapter().apply {
            submitMyList(productsList)
        }
        recyclerView.adapter = productsRecyclerAdapter
        recyclerView.startLayoutAnimation()

    }

    private fun initProductsRecyclerInOfflineMode(
        productsList: List<Product>,
        recyclerView: RecyclerView,
    ) {

        recyclerView.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL,
            false
        )
        val productsRecyclerAdapter = ProductsOfflineRecyclerAdapter().apply {
            submitMyList(productsList)
        }
        recyclerView.adapter = productsRecyclerAdapter
        recyclerView.startLayoutAnimation()

    }
}