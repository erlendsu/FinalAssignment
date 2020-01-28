package com.auas.finalassignment.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.auas.finalassignment.model.Trip
import kotlinx.android.synthetic.main.item_trip.view.*

class TripAdapter(private val trips: List<Trip>, val adapterOnClick : (View, Int) -> Unit) : RecyclerView.Adapter<TripAdapter.ViewHolder>() {

    /**
     * Creates and returns a ViewHolder object, inflating a custom layout called item_trip.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(com.auas.finalassignment.R.layout.item_trip, parent, false)
        )
    }

    /**
     * Returns the size of the list
     */
    override fun getItemCount(): Int {
        return trips.size
    }

    /**
     * Called by RecyclerView to display the data at the specified position.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(trips[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(trip: Trip) {
            itemView.tvTitle.text = trip.title
            itemView.tvDestination.text = trip.destination
            itemView.tvAirline.text = trip.airline
            itemView.tvCost.text = trip.cost
            itemView.tvExtra.text = trip.extra
            if(trip.completed) itemView.tvCompleted.visibility = View.VISIBLE

            itemView.moreBtn.setOnClickListener { adapterOnClick(itemView, adapterPosition) }
        }
    }
}