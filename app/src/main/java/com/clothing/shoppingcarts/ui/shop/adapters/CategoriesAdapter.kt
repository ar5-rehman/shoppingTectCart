package com.clothing.shoppingcarts.ui.shop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.clothing.shoppingcarts.R
import com.clothing.shoppingcarts.base.navigation.NavManager
import com.clothing.shoppingcarts.data.models.CategoriesDTOItem
import com.clothing.shoppingcarts.data.models.Category

class CategoriesAdapter(
    private val context: Context,
    private var list: List<Category>, navManager: NavManager
) : RecyclerView.Adapter<CategoriesAdapter.SectionsViewHolder>() {

    private var categoryAdapter: RecyclerView.Adapter<*>? = null
    private var linearLayoutManager: LinearLayoutManager? = null
    private val snapHelper = PagerSnapHelper()
    private val navManager: NavManager = navManager

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SectionsViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.sub_categories_layout, parent, false)
        val myViewHolder = SectionsViewHolder(view)
        linearLayoutManager = LinearLayoutManager(context)
        linearLayoutManager!!.orientation = RecyclerView.VERTICAL
        myViewHolder.subCategoriesRecycler.layoutManager = linearLayoutManager

        return myViewHolder
    }

    override fun onBindViewHolder(holder: SectionsViewHolder, position: Int) {
        val item: Category = list[position]

        holder.categoryTitle.text = item.name
        snapHelper.attachToRecyclerView(holder.subCategoriesRecycler)

        categoryAdapter = SubCategoriesItemAdapter(list[position].categories, context, navManager)
        holder.subCategoriesRecycler.adapter = categoryAdapter


    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setCategoriesData(categoriesList: List<Category>){
        this.list = categoriesList
        notifyDataSetChanged()
    }

    class SectionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var categoryTitle: TextView = itemView.findViewById(R.id.tv_sub_categories_title)
        var subCategoriesRecycler: RecyclerView = itemView.findViewById(R.id.rv_sub_categories)

    }
}
