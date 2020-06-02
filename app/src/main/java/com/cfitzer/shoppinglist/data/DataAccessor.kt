package com.cfitzer.shoppinglist.data

import androidx.lifecycle.MutableLiveData
import com.cfitzer.shoppinglist.models.ShoppingListEntry

interface DataAccessor {
    fun listOfShoppingListEntries(): MutableLiveData<MutableList<ShoppingListEntry>>
    fun arrayOfItemTypes(): Array<String>
    fun writeShoppingListEntry(entry: ShoppingListEntry)
    fun deleteShoppingListEntry(entry: ShoppingListEntry)
}