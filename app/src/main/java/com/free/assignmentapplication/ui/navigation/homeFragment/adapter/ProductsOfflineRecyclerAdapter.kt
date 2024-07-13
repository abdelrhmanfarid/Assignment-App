package com.free.assignmentapplication.ui.navigation.homeFragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.free.assignmentapplication.R
import com.free.assignmentapplication.data.local.room.Product
import com.free.assignmentapplication.databinding.ProductsRecyclerItemBinding

class ProductsOfflineRecyclerAdapter (private var myList: List<Product> = listOf()) :
    RecyclerView.Adapter<ProductsOfflineRecyclerAdapter.ViewHolder>() {
    var productsArrayList: List<Product> = ArrayList()

    private var parent: ViewGroup? = null


    init {
        this.productsArrayList = myList
    }



    fun submitMyList(
        myList: List<Product>,
    ) {
        this.productsArrayList = myList


        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemBinding: ProductsRecyclerItemBinding =
            ProductsRecyclerItemBinding.inflate(layoutInflater, parent, false)
        this.parent = parent
        return ViewHolder(itemBinding)
    }

    override fun getItemCount(): Int {
        return productsArrayList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.binding.productTitleTextView.text = productsArrayList[position].title
        holder.binding.productDescriptionTextView.text = productsArrayList[position].description
        holder.binding.priceTextView.text = productsArrayList[position].price.toString()

        Glide.with(parent!!.context).load(productsArrayList[position].image)
            .centerCrop().into(holder.binding.productImageView)
    }

    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)
        animate(holder.itemView)
    }

    private fun animate(view: View) {
        val animation = AnimationUtils.loadAnimation(view.context, R.anim.anim_fade_in)
        view.startAnimation(animation)
    }


    inner class ViewHolder(var binding: ProductsRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }
}