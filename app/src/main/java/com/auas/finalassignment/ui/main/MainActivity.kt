package com.auas.finalassignment.ui.main

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.auas.finalassignment.model.Trip
import com.auas.finalassignment.ui.add.AddActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import android.view.View
import android.widget.ImageButton
import com.auas.finalassignment.R
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.appcompat.widget.PopupMenu
import com.auas.finalassignment.ui.add.EXTRA_ID


class MainActivity : AppCompatActivity() {

    private val trips = arrayListOf<Trip>()
    private val tripAdapter = TripAdapter(trips) { item, position -> doMenuClick(item, position) }

    private lateinit var viewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        setTitle(R.string.app_name)

        initViews()
        initViewModel()
    }



    private fun initViews() {

        // Initialize the recycler view with a linear layout manager, adapter, decorator and swipe callback.
        rvTrips.layoutManager = LinearLayoutManager(this@MainActivity, RecyclerView.VERTICAL, false)
        rvTrips.adapter = tripAdapter
        rvTrips.addItemDecoration(DividerItemDecoration(this@MainActivity, DividerItemDecoration.VERTICAL))
        createItemTouchHelper().attachToRecyclerView(rvTrips)

        // Clicking floating action button will call startAddActivity.
        fab.setOnClickListener {
            startAddActivity(ADD_TRIP_REQUEST_CODE, null)
        }

    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java)

        // Observe trips from the view model, update the list when the data is changed.
        viewModel.trips.observe(this, Observer { trips ->
            this@MainActivity.trips.clear()
            this@MainActivity.trips.addAll(trips)
            tripAdapter.notifyDataSetChanged()
        })
    }


    /**
     * Create a touch helper to recognize when a user swipes an item from a recycler view.
     * An ItemTouchHelper enables touch behavior (like swipe and move) on each ViewHolder,
     * and uses callbacks to signal when a user is performing these actions.
     */
    private fun createItemTouchHelper(): ItemTouchHelper {

        // Callback which is used to create the ItemTouch helper. Only enables left swipe.
        // Use ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) to also enable right swipe.
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

            // Enables or Disables the ability to move items up and down.
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            // Callback triggered when a user swiped an item.
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteTrip(trips[position])
            }
        }
        return ItemTouchHelper(callback)
    }

    /**
     * Start [AddActivity].
     */
    private fun startAddActivity(requestCode: Int, tripId: Long?) {
        val intent = Intent(this, AddActivity::class.java)
        if (tripId != null) {
            intent.putExtra(EXTRA_ID, tripId)
        }
        startActivityForResult(
            intent,
            requestCode
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val trip = data!!.getParcelableExtra<Trip>(AddActivity.EXTRA_TRIP)
            when (requestCode) {
                ADD_TRIP_REQUEST_CODE -> {
                    viewModel.insertTrip(trip)
                }
                EDIT_TRIP_REQUEST_CODE -> {
                    viewModel.updateTrip(trip)
                }
            }
        }
    }

    private fun doMenuClick(item: View, position: Int) {
        val moreBtn: ImageButton = item.findViewById(R.id.moreBtn)

        val popup = PopupMenu(this@MainActivity, moreBtn)
        popup.inflate(R.menu.menu_trip)
        val trip = trips[position]

        if (trip.completed) {
            popup.menu.findItem(R.id.menuComplete).title = "Undo completion"
        }

        popup.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.menuComplete -> {
                    trip.completed = !trip.completed
                    viewModel.updateTrip(trip)
                }
                R.id.menuDelete -> {
                    viewModel.deleteTrip(trip)
                }
                R.id.menuEdit -> {
                    startAddActivity(EDIT_TRIP_REQUEST_CODE, trip.id)
                }
                R.id.menuCancel -> {
                    popup.dismiss()
                }
            }
            false
        }
        val menuHelper = MenuPopupHelper(this@MainActivity, popup.menu as MenuBuilder, moreBtn)
   //     menuHelper.setForceShowIcon(true)
        menuHelper.show()
    }


    companion object {
        const val ADD_TRIP_REQUEST_CODE = 100
        const val EDIT_TRIP_REQUEST_CODE = 200
    }
}
