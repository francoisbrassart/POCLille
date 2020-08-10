package com.example.poclille.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.poclille.R
import com.example.poclille.controller.fragments.RoubaixDetailFragment
import com.example.poclille.model.Fresco
import java.util.*

class RoubaixListAdapter(private val dataSet: ArrayList<Fresco>) : RecyclerView.Adapter<RoubaixListAdapter.RoubaixViewHolder>() {

    inner class RoubaixViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val mNameTextView: TextView = itemView.findViewById<View>(R.id.list_fresco_name) as TextView
        private val mStreetAddressTextView: TextView = itemView.findViewById<View>(R.id.list_fresco_street_address) as TextView
        private val mDescriptionTextView: TextView = itemView.findViewById<View>(R.id.list_fresco_description) as TextView
        private lateinit var mFresco:Fresco

        fun display(fresco:Fresco){
            mFresco=fresco
            mNameTextView.text=fresco.name
            mStreetAddressTextView.text=fresco.streetAdress
            mDescriptionTextView.text=fresco.description
        }
        init {
            //on click -> go to roubaix detail fragment
            itemView.setOnClickListener {
                val context=itemView.context as FragmentActivity
                context.supportFragmentManager.beginTransaction().replace(R.id.activity_main_frame_layout,RoubaixDetailFragment(mFresco)).addToBackStack(null).commit()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoubaixViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.list_freco, parent, false)
        return RoubaixViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: RoubaixViewHolder, position: Int) {
        holder.display(dataSet[position])
    }
}