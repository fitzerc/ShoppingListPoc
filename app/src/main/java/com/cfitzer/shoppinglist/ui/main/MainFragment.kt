package com.cfitzer.shoppinglist.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cfitzer.shoppinglist.MainViewModelFactory
import com.cfitzer.shoppinglist.R
import com.cfitzer.shoppinglist.models.ShoppingListEntry
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.coroutines.launch

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
        viewModel = ViewModelProviders.of(this, MainViewModelFactory()).get(MainViewModel::class.java)

        val baseTypes = viewModel.baseTypes
        //val items = viewModel.items
        var items = mutableListOf(ShoppingListEntry("Loading Items", ""))

        viewManager = LinearLayoutManager(this.context)
        viewAdapter = ShoppingRowAdapter(items, viewModel)

        this.spinner!!.onItemSelectedListener = this
        spinner!!.adapter = this.GetArrayAdapter(baseTypes)

        this.addItemBtn.setOnClickListener {
            val name = this.addTxt.text.toString()
            val type = baseTypes[this.selectedItem]

            items.add(items.size, ShoppingListEntry(name, type))
            viewModel.WriteShoppingListItem(ShoppingListEntry(name, type))

            viewAdapter.notifyDataSetChanged()
            this.addTxt.setText("")
        }

        listView.apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }

        viewModel.getItems().observe(this, Observer { it ->
            items.removeAt(0)
            it.map { x -> items.add(x) }
            viewAdapter.notifyDataSetChanged()
        })
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        this.selectedItem = position
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

    private fun GetArrayAdapter(baseTypes: Array<String>): ArrayAdapter<String>{
        val aa = ArrayAdapter(this.context!!, android.R.layout.simple_spinner_item, baseTypes)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        return aa
    }
}
