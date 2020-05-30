package com.cfitzer.shoppinglist.ui.main

import androidx.lifecycle.ViewModel
import com.cfitzer.shoppinglist.data.DataAccessor

class MainViewModel (private val dataAccessor: DataAccessor) : ViewModel() {
    val baseTypes = dataAccessor.arrayOfItemTypes()
    val items = dataAccessor.listOfShoppingListEntries()
}
