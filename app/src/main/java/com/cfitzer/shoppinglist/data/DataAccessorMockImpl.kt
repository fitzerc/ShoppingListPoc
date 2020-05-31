package com.cfitzer.shoppinglist.data

import com.cfitzer.shoppinglist.models.ShoppingListEntry

    class DataAccessorMockImpl: DataAccessor {
    override fun listOfShoppingListEntries(): MutableList<ShoppingListEntry> {
        return mutableListOf<ShoppingListEntry>(
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
    }

    override fun arrayOfItemTypes(): Array<String> {
        return arrayOf("Grocery", "Hardware", "Costco", "Clothing", "Bookstore")
    }
}