package com.cfitzer.shoppinglist.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.Callback.makeMovementFlags
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.cfitzer.shoppinglist.R
import com.cfitzer.shoppinglist.models.ShoppingListEntry
import kotlinx.android.synthetic.main.shoppingitem_row.view.*
import java.util.*


class ShoppingRowAdapter(private val items: MutableList<ShoppingListEntry>, private val viewModel: MainViewModel) :

    RecyclerView.Adapter<ShoppingRowAdapter.ShoppingListViewHolder>(), ItemTouchHelperAdapter {

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

    fun getMovementFlags(recyclerView: RecyclerView?, viewHolder: ViewHolder?): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(
            dragFlags,
            swipeFlags
        )
    }

    fun isLongPressDragEnabled(): Boolean {
        return true
    }

    fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    private fun removeItemFromView(position: Int) {
        viewModel.DeleteShoppingListItem(items[position])
        items.removeAt(position)
        notifyItemRemoved(position)

        notifyItemRangeChanged(position, items.size)
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = items.size

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(items, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(items, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
    }
}

class SimpleItemTouchHelperCallback(private val mAdapter: ItemTouchHelperAdapter) :
    ItemTouchHelper.Callback() {
    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
        TODO("Not yet implemented")
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return false
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(
            dragFlags,
            swipeFlags
        )
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: ViewHolder, target: ViewHolder): Boolean {
        mAdapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }
}

interface ItemTouchHelperAdapter {
    fun onItemMove(fromPosition: Int, toPosition: Int)
}