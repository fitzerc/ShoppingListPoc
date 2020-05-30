package com.cfitzer.shoppinglist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cfitzer.shoppinglist.data.DataAccessorFirebaseImpl
import com.cfitzer.shoppinglist.ui.main.MainViewModel

class MainViewModelFactory(
): ViewModelProvider.NewInstanceFactory() {
    override fun <T: ViewModel> create(modelClass:Class<T>): T {
        return MainViewModel(DataAccessorFirebaseImpl()) as T
    }
}
