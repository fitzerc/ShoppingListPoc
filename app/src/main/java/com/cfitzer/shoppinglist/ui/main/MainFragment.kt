package com.cfitzer.shoppinglist.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cfitzer.shoppinglist.R
import com.cfitzer.shoppinglist.data.DataAccessorFirebaseImpl
import com.cfitzer.shoppinglist.models.ShoppingListEntry
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment() : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var selectedItem = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel

        val dataAccess = DataAccessorFirebaseImpl()
        val baseTypes = dataAccess.arrayOfItemTypes()
        val items = dataAccess.listOfShoppingListEntries()

        viewManager = LinearLayoutManager(this.context)
        viewAdapter = ShoppingRowAdapter(items)

        this.spinner!!.onItemSelectedListener = this
        val aa = ArrayAdapter(this.context!!, android.R.layout.simple_spinner_item, baseTypes)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner!!.adapter = aa

        this.addItemBtn.setOnClickListener {
            if (this.selectedItem == -1) {
                Toast.makeText(it.context, "Select a Type", Toast.LENGTH_LONG).show()
            }
            else {
                items.add(items.size, ShoppingListEntry(this.addTxt.text.toString(), baseTypes[this.selectedItem]))

                viewAdapter.notifyDataSetChanged()
                this.addTxt.setText("")
            }
        }

        listView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        this.selectedItem = position
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

}
