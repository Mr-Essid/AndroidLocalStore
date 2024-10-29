package edu.google.compose.databasepracticeroom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.google.compose.databasepracticeroom.database.Product
import edu.google.compose.databasepracticeroom.database.TagProduct

class ProductListAdapter(val productsList: Array<TagProduct>): RecyclerView.Adapter<ProductListAdapter.ItemViewHolder>() {


    class ItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var label: TextView = view.findViewById(R.id.item_name)
        var id: TextView = view.findViewById(R.id.item_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val inflated = LayoutInflater.from(parent.context).inflate(R.layout.product_item, parent, false)
        return ItemViewHolder(inflated)
    }

    override fun getItemCount() = productsList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = productsList[position]
        holder.label.text = item.product.label
        holder.id.text = item.product.productId.toString()
    }

}