package com.clothing.shoppingcarts.ui.shop.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import androidx.recyclerview.widget.RecyclerView
import com.clothing.shoppingcarts.R
import com.clothing.shoppingcarts.base.navigation.NavManager
import com.clothing.shoppingcarts.data.models.CategoriesClothingDetail
import com.clothing.shoppingcarts.data.models.Category
import com.clothing.shoppingcarts.ui.shop.ShopFragmentDirections

class SubCategoriesItemAdapter(val list: List<CategoriesClothingDetail>, val context: Context,  navManager: NavManager) :
    RecyclerView.Adapter<SubCategoriesItemAdapter.SectionItemsViewHolder>() {

    private val navManager: NavManager = navManager

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SectionItemsViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.sub_category_names_layout, parent, false)
        return SectionItemsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SectionItemsViewHolder, position: Int) {
        val subCategory: CategoriesClothingDetail = list[position]
        holder.txtOne.text = subCategory.name
        holder.layout.setOnClickListener {
            val action = ShopFragmentDirections.actionShopToWomenClothDetailsFragment(subCategory.name,subCategory.id)
            navManager.navigate(action)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class SectionItemsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var layout: ConstraintLayout = itemView.findViewById(R.id.layout)
        var txtOne: TextView = itemView.findViewById(R.id.tv_sub_categories_name)

    }
}
