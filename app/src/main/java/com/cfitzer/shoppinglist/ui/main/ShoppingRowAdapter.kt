package com.cfitzer.shoppinglist.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import kotlinx.android.synthetic.main.shoppingitem_row.*
import androidx.recyclerview.widget.RecyclerView
import com.cfitzer.shoppinglist.R
import kotlinx.android.synthetic.main.shoppingitem_row.view.*

class ShoppingRowAdapter(private val myDataset: Array<String>, private val types: Array<String>) :
    RecyclerView.Adapter<ShoppingRowAdapter.ShoppingListViewHolder>() {

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder.
    // Each data item is just a string in this case that is shown in a TextView.
    class ShoppingListViewHolder(val view: View) : RecyclerView.ViewHolder(view)


    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup,
                                    viewType: Int): ShoppingRowAdapter.ShoppingListViewHolder {
        // create a new view
        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.shoppingitem_row, parent, false)
        // set the view's size, margins, paddings and layout parameters
        return ShoppingListViewHolder(textView)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.view.tvShoppingItem.text = myDataset[position]
        holder.view.tvShoppingItemType.text = types[position]
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = myDataset.size
}