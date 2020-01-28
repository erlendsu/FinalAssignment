package com.auas.finalassignment.ui.add

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.auas.finalassignment.R
import com.auas.finalassignment.model.Trip
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.content_add.*

const val EXTRA_ID = "EXTRA_ID"

class AddActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        setSupportActionBar(toolbar)

        initViews()
    }

    private fun initViews() {
        fab.setOnClickListener { onSaveClick() }

        // Used for creating a back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun onSaveClick() {
        val newId = intent.extras?.getLong(EXTRA_ID)
        if (etDestination.text.toString().isNotBlank()) {
            val trip = Trip(etTitle.text.toString(), etDestination.text.toString(), etAirline.text.toString(), etCost.text.toString(), etExtra.text.toString(), id = newId)
            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_TRIP, trip)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        } else {
            Toast.makeText(this, getString(R.string.msg_empty_trip), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            android.R.id.home -> { // Identifies when the user has clicked the back button
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        const val EXTRA_TRIP = "EXTRA_TRIP"
    }

}


