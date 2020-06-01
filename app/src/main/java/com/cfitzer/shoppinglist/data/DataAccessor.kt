package com.cfitzer.shoppinglist.data

import com.cfitzer.shoppinglist.models.ShoppingListEntry

interface DataAccessor {
    fun listOfShoppingListEntries(): MutableList<ShoppingListEntry>
    fun arrayOfItemTypes(): Array<String>
    fun writeShoppingListEntry(entry: ShoppingListEntry)
    fun deleteShoppingListEntry(entry: ShoppingListEntry)
}