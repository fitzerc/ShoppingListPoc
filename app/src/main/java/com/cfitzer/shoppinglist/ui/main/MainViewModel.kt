package com.cfitzer.shoppinglist.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cfitzer.shoppinglist.data.DataAccessor
import com.cfitzer.shoppinglist.models.ShoppingListEntry

class MainViewModel (private val dataAccessor: DataAccessor) : ViewModel() {
    public val DEFAULT_ITEM_NAME = "Loading Items"
    val baseTypes = dataAccessor.arrayOfItemTypes()
    val items = dataAccessor.listOfShoppingListEntries()

    fun WriteShoppingListItem(entry: ShoppingListEntry) {
        dataAccessor.writeShoppingListEntry(entry)
    }

    fun DeleteShoppingListItem(entry: ShoppingListEntry) {
        dataAccessor.deleteShoppingListEntry(entry)
    }

    fun getItems(): LiveData<MutableList<ShoppingListEntry>> {
        return dataAccessor.listOfShoppingListEntries()
    }
}
