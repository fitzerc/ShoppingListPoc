package com.cfitzer.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cfitzer.shoppinglist.models.ShoppingListEntry

    class DataAccessorMockImpl: DataAccessor {
        override fun listOfShoppingListEntries(): MutableLiveData<MutableList<ShoppingListEntry>> {
            val list = mutableListOf<ShoppingListEntry>(
                ShoppingListEntry("Bananas", "Grocery"),
                ShoppingListEntry("Pizza", "Grocery"),
                ShoppingListEntry("Seltzer", "Grocery"),
                ShoppingListEntry("Duct Tape", "Hardware"),
                ShoppingListEntry("Paper Towels", "Costco"),
                ShoppingListEntry("Frozen Veggies", "Costco"),
                ShoppingListEntry("Detergent", "Costco"),
                ShoppingListEntry("Jeans", "Clothing"),
                ShoppingListEntry("T-Shirts", "Clothing"),
                ShoppingListEntry("Clean Programming", "Bookstore")
            )

            var res = MutableLiveData<MutableList<ShoppingListEntry>>()
            res.value = list

            return res
        }

        override fun arrayOfItemTypes(): Array<String> {
            return arrayOf("Grocery", "Hardware", "Costco", "Clothing", "Bookstore")
        }

        override fun writeShoppingListEntry(entry: ShoppingListEntry) {
            TODO("Not yet implemented")
        }

        override fun deleteShoppingListEntry(entry: ShoppingListEntry) {
            TODO("Not yet implemented")
        }
    }