package com.cfitzer.shoppinglist.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.cfitzer.shoppinglist.R
import com.cfitzer.shoppinglist.models.ShoppingListEntry
import kotlinx.android.synthetic.main.shoppingitem_row.view.*

class ShoppingRowAdapter(private val items: MutableList<ShoppingListEntry>) :

    RecyclerView.Adapter<ShoppingRowAdapter.ShoppingListViewHolder>(){

    class ShoppingListViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ShoppingListViewHolder {
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.shoppingitem_row, parent, false)
        return ShoppingListViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        holder.view.tvShoppingItem.text = items[position].name
        holder.view.tvShoppingItemType.text = items[position].type

        holder.view.btnDelete.setOnClickListener {
            val item = items[position].name
            removeItemFromView(position)
            Toast.makeText(it.context, "$item Removed", Toast.LENGTH_LONG).show()
        }
    }

    private fun removeItemFromView(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)

        notifyItemRangeChanged(position, items.size)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size
}