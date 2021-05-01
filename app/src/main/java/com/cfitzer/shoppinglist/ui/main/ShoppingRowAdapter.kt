package com.cfitzer.shoppinglist.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cfitzer.shoppinglist.R
import com.cfitzer.shoppinglist.models.ShoppingListEntry
import com.cfitzer.shoppinglist.ui.main.touchhelper.ItemTouchHelperAdapter
import kotlinx.android.synthetic.main.shoppingitem_row.view.*
import java.util.*
import android.widget.AdapterView
import kotlinx.android.synthetic.main.main_fragment.*


class ShoppingRowAdapter(private val items: MutableList<ShoppingListEntry>, private val viewModel: MainViewModel) :

RecyclerView.Adapter<ShoppingRowAdapter.ShoppingListViewHolder>(), ItemTouchHelperAdapter, AdapterView.OnItemSelectedListener{
    private lateinit var parent: ViewGroup
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var selectedItem = -1
    private var currentType = ""
    private var currentPosition = 0

    class ShoppingListViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoppingListViewHolder {
        this.parent = parent

        val textView = LayoutInflater.from(parent.context)
            .inflate(R.layout.shoppingitem_row, parent, false)

        return ShoppingListViewHolder(textView)
    }

    override fun onBindViewHolder(holder: ShoppingListViewHolder, position: Int) {

        holder.view.tvShoppingItem.text = items[position].name
        this.currentType = items[position].type
        val baseTypes = viewModel.baseTypes
        holder.view.spinner!!.onItemSelectedListener = this
        holder.view.spinner!!.adapter = this.getArrayAdapter(baseTypes)

        holder.view.spinner.setSelection(this.lookupIndex(baseTypes, this.currentType));


        holder.view.btnDelete.setOnClickListener {
            val item = items[position].name
            removeItemFromView(position)
            Toast.makeText(it.context, "$item Removed", Toast.LENGTH_LONG).show()
        }
    }

    private fun removeItemFromView(position: Int) {
        viewModel.DeleteShoppingListItem(items[position])
        items.removeAt(position)
        notifyItemRemoved(position)

        notifyItemRangeChanged(position, items.size)
    }

    override fun getItemCount() = items.size

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        if (fromPosition < toPosition) {
            moveItemDown(fromPosition, toPosition)
        } else {
            moveItemUp(fromPosition, toPosition)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    private fun moveItemDown(fromPosition: Int, toPosition: Int) {
        for (i in fromPosition until toPosition) {
            Collections.swap(items, i, i + 1)
        }
    }

    private fun moveItemUp(fromPosition: Int, toPosition: Int) {
        for (i in fromPosition downTo toPosition + 1) {
            Collections.swap(items, i, i - 1)
        }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        val item = items[currentPosition]

        if (!selectionChanged(item, position)) {
            return
        }

        selectedItem = position
        updateItemType(item, position)
    }

    private fun selectionChanged(item: ShoppingListEntry, position: Int): Boolean {
        return item.name != viewModel.DEFAULT_ITEM_NAME &&
                item.type != viewModel.baseTypes[position]
    }

    private fun updateItemType(item: ShoppingListEntry, position: Int) {
        viewModel.DeleteShoppingListItem(item)

        viewModel.WriteShoppingListItem(
            ShoppingListEntry(item.name, viewModel.baseTypes[position])
        )
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun getArrayAdapter(baseTypes: Array<String>): ArrayAdapter<String> {
        val aa = ArrayAdapter(this.parent.context!!, R.layout.small_spinner, baseTypes)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        return aa
    }

    private fun lookupIndex(list: Array<String>, currentItem: String): Int {
        return list.indexOf(currentItem)
    }
}
