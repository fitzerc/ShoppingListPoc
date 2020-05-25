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
import kotlinx.android.synthetic.main.main_fragment.*

class MainFragment() : Fragment(), AdapterView.OnItemSelectedListener {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private var selectedItem = -1
    private val base_types = arrayOf("Grocery", "Hardware", "Costco", "Clothing", "Bookstore")

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

        val myDataset = mutableListOf("Bananas", "Pizza", "Seltzer", "Duct Tape", "Paper Towels", "Frozen Veggies", "Detergent", "Jeans", "T-Shirt", "Clean Programming")
        val types = mutableListOf("Grocery", "Grocery", "Grocery", "Hardware", "Costco", "Costco", "Costco", "Clothing", "Clothing", "Bookstore")

        viewManager = LinearLayoutManager(this.context)
        viewAdapter = ShoppingRowAdapter(myDataset, types)

        this.spinner!!.setOnItemSelectedListener(this)

        // Create an ArrayAdapter using a simple spinner layout and languages array
        val aa = ArrayAdapter(this.context!!, android.R.layout.simple_spinner_item, base_types)
        // Set layout to use when the list of choices appear
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Set Adapter to Spinner
        spinner!!.adapter = aa

        this.addItemBtn.setOnClickListener {
            if (this.selectedItem == -1) {
                Toast.makeText(it.context, "Select a Type", Toast.LENGTH_LONG).show()
            }
            else {
                myDataset.add(myDataset.size, this.addTxt.text.toString())
                types.add(types.size, this.base_types[this.selectedItem])

                viewAdapter.notifyDataSetChanged()
                this.addTxt.setText("")
            }
        }

        listView.apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        this.selectedItem = position
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}

}
