package com.example.poclille.controller

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.poclille.R
import com.example.poclille.controller.fragments.RoubaixDetailFragment
import com.example.poclille.model.Fresco
import kotlinx.android.synthetic.main.list_freco.view.*
import java.util.*

class RoubaixListAdapter(private val dataSet: ArrayList<Fresco>) : RecyclerView.Adapter<RoubaixListAdapter.RoubaixViewHolder>() {

    inner class RoubaixViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var mFresco:Fresco

        fun display(fresco:Fresco){
            mFresco=fresco
            itemView.listFrescoName.text=fresco.name
            itemView.listFrescoStreetAddress.text=fresco.streetAdress
            itemView.listFrescoDescription.text=fresco.description
        }
        init {
            //on click -> go to roubaix detail fragment
            itemView.setOnClickListener {
                val context=itemView.context as FragmentActivity
                context.supportFragmentManager.beginTransaction().replace(R.id.ActivityMainFrameLayout,RoubaixDetailFragment(mFresco)).addToBackStack(null).commit()
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