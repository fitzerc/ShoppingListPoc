package com.cfitzer.shoppinglist.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cfitzer.shoppinglist.R
import kotlinx.android.synthetic.main.main_fragment.*
import kotlinx.android.synthetic.main.shoppingitem_row.*

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

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
}
