package com.cfitzer.shoppinglist.data

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.cfitzer.shoppinglist.models.ShoppingListEntry
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class DataAccessorFirebaseImpl : DataAccessor {
    private val db = Firebase.firestore
    private val SHOPPING_LIST_COLLECTION_NAME = "shoppingListItems"

    override fun listOfShoppingListEntries(): MutableLiveData<MutableList<ShoppingListEntry>> {
        var shoppingList = mutableListOf<ShoppingListEntry>()

        var resultData = MutableLiveData<MutableList<ShoppingListEntry>>()

        db.collection(SHOPPING_LIST_COLLECTION_NAME)
            .get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    shoppingList.add(ShoppingListEntry(document.data["name"].toString(), document.data["type"].toString()))
                }
                resultData.value = shoppingList
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents.", exception)
            }

        return resultData
    }

    override fun arrayOfItemTypes(): Array<String> {
        return arrayOf("Grocery", "Costco", "Hardware", "Clothing", "Bookstore", "Misc")
    }

    override fun writeShoppingListEntry(entry: ShoppingListEntry) {
        db.collection(SHOPPING_LIST_COLLECTION_NAME)
            .add(preparedEntryOfShoppingList(entry))
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    override fun deleteShoppingListEntry(entry: ShoppingListEntry) {
        val shoppingListRef = db.collection(SHOPPING_LIST_COLLECTION_NAME)
        shoppingListRef.whereEqualTo("name", entry.name)
            .whereEqualTo("type", entry.type)
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    document.reference.delete()
                }
            }
    }

    private fun preparedEntryOfShoppingList(entry: ShoppingListEntry): HashMap<String, String> {
        return hashMapOf(
            "name" to entry.name,
            "type" to entry.type
        )
    }
}