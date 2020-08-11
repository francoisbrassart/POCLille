package com.example.poclille.controller

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.poclille.R
import com.example.poclille.model.VLille
import kotlinx.android.synthetic.main.list_vlille.view.*
import java.util.*

class VLilleListAdapter(private val dataSet: ArrayList<VLille>): RecyclerView.Adapter<VLilleListAdapter.VLilleViewHolder>() {

    inner class VLilleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var mVLille:VLille

        fun display(vlille: VLille){
            mVLille=vlille
            val context=itemView.context as FragmentActivity
            itemView.listVlilleName.text=vlille.name
            itemView.listVlilleAddress.text=vlille.address
            itemView.listVlilleVelosDispo.text=context.getString(R.string.bikes_available,vlille.velosDispo)
            itemView.listVlillePlacesDispo.text=context.getString(R.string.spots_available,vlille.placesDispo)
        }
        init {
            itemView.setOnClickListener {
                sendLocation(mVLille, itemView)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VLilleViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view: View = inflater.inflate(R.layout.list_vlille, parent, false)
        return VLilleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: VLilleViewHolder, position: Int) {
        holder.display(dataSet[position])
    }

    //share location of selected vlille station
    private fun sendLocation(vlille: VLille,view:View){
        val uri: String = java.lang.String.format(Locale.ENGLISH, "geo:%f,%f?q=%f,%f()", vlille.location[0], vlille.location[1], vlille.location[0], vlille.location[1])
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(uri))
        view.context.startActivity(intent)
    }

}